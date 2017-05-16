package com.struggle.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xuchengdongxcd@126.com on 2016/12/1.
 */
@Component
public class QiNiuClient implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(QiNiuClient.class);

    @Value("${qiniu.AccessKey}")
    private String accessKey;

    @Value("${qiniu.SecretKey}")
    private String secretKey;

    @Value("${qiniu.bucketname}")
    private String bucketname;

    /**
     * 密钥配置
     */
    private Auth auth;

    /**
     * 上传对象
     */
    private UploadManager uploadManager;

    public void afterPropertiesSet() throws Exception {
        auth = Auth.create(accessKey, secretKey);
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
    }

    private String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String upload(String uploadFilePath, String saveFileName) throws IOException {
        try {
            Response res = uploadManager.put(uploadFilePath, saveFileName, getUpToken());

            String bodyString = res.bodyString();

            LOGGER.info("qiniu uplaod file success response.bodyString={}", bodyString);

            return bodyString;
        } catch (QiniuException e) {
            Response r = e.response;
            LOGGER.error(String.format("qiniu uplaod file fail response=%s", r), e);
            try {
                LOGGER.error("qiniu uplaod file fail response.bodyString={}", r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }


}
