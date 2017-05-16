package com.struggle.timer;

import com.alibaba.fastjson.JSONObject;
import com.struggle.core.AtomicCondition;
import com.struggle.domain.LoginQrCode;
import com.struggle.domain.LoginQrCodeBuilder;
import com.struggle.qiniu.QiNiuClient;
import com.struggle.redis.RedisQueue;
import com.struggle.util.URLUtil;
import com.struggle.util.UUIDGenerator;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author xuchengdongxcd@126.com on 2016/12/3.
 */
public class QRCodeThread implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(QRCodeThread.class);
    private static final int QRCODE_SIZE = 140;

    private RedisQueue<Object> redisQueue;
    private QiNiuClient qiNiuClient;
    private LoginQrCodeBuilder loginQrCodeBuilder;

    private String lgCheck;

    QRCodeThread(RedisQueue<Object> redisQueue, QiNiuClient qiNiuClient, LoginQrCodeBuilder loginQrCodeBuilder,
                 String lgCheck) {
        this.redisQueue = redisQueue;
        this.qiNiuClient = qiNiuClient;
        this.loginQrCodeBuilder = loginQrCodeBuilder;
        this.lgCheck = lgCheck;
    }

    public void run() {
        while (redisQueue.isNotFull()) {
            String lgToken = UUIDGenerator.getId(false);

            String longLink = URLUtil.appendParam(lgCheck, "lgToken", lgToken);

            // TODO 生成短链接

            final File file = QRCode.from(longLink).withSize(QRCODE_SIZE, QRCODE_SIZE).to(ImageType.PNG).file(UUIDGenerator.getId(false));

            LoginQrCode loginQrCode = loginQrCodeBuilder.newLoginQrCode(file.getName(), lgToken);
            try {
                redisQueue.put(JSONObject.toJSONString(loginQrCode), new AtomicCondition<String>() {
                    public String execute() {
                        String bodyString = null;
                        try {
                            bodyString = qiNiuClient.upload(file.getAbsolutePath(), file.getName());
                        } catch (Exception e) {
                            LOGGER.error("qiniu uplaod file exception!", e);
                        }
                        return bodyString;
                    }

                    public boolean isSuccess(String bodyString) {
                        return bodyString != null;
                    }
                });
            } catch (InterruptedException e) {
                LOGGER.error("com.struggle.redis.RedisQueue.put exception!", e);
            }
        }
    }
}
