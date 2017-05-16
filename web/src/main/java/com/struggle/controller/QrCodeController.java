package com.struggle.controller;

import com.alibaba.fastjson.JSONObject;
import com.struggle.constant.BusinessCode;
import com.struggle.domain.LoginQrCode;
import com.struggle.domain.Result;
import com.struggle.redis.RedisClient;
import com.struggle.redis.RedisQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuchengdongxcd@126.com on 2016/11/29.
 */
@RestController
@RequestMapping("/qrcodelogin")
public class QrCodeController {

    private static final Logger LOGGER = LogManager.getLogger(QrCodeController.class);

    private static final String QRCODE_USING_NAMESPACE = "USING";
    private static final String QRCODE_AUTHORIZE_NAMESPACE = "QRCODE:AUTHORIZE:";
    private static final String QRCODE_SCANNED_NAMESPACE = "QRCODE:SCANNED:";

    @Autowired
    private RedisQueue<String> redisQueue;

    @Autowired
    @Qualifier("redisClient4StrVal")
    private RedisClient<String> redisClient;


    @Autowired
    @Qualifier("publicRedisTemplate")
    private RedisTemplate<String, String> publicRedisTemplate;

    @RequestMapping("/generateQRCode4Login")
    public Result generateQRCode4Login() {
        try {
            String loginQrCodeJson = redisQueue.take();
            LoginQrCode loginQrCode = JSONObject.parseObject(loginQrCodeJson, LoginQrCode.class);
            redisClient.set(loginQrCode.getLgToken(), loginQrCodeJson, loginQrCode.getExpire(), QRCODE_USING_NAMESPACE);
            return Result.success(loginQrCode);
        } catch (Exception e) {
            LOGGER.error("get qrcode exception!", e);
        }
        return Result.fail("exception!");
    }

    @RequestMapping("/qrcodeLoginCheck")
    public Result qrcodeLoginCheck(@RequestParam(value = "lgToken") String lgToken) {
        try {
            String loginQrCodeJson = redisClient.get(lgToken, QRCODE_USING_NAMESPACE);

            // lgToken expired
            if (loginQrCodeJson == null) {
                return Result.fail(BusinessCode.INVALID, "QRCode expired!");
            }

            // authorize
            String autoLoginUrl = publicRedisTemplate.opsForValue().get(QRCODE_AUTHORIZE_NAMESPACE + lgToken);
            if (autoLoginUrl != null) {
                return new Result<Object>(BusinessCode.AUTHORIZED, "Authorization succeeded!", autoLoginUrl);
            }

            // scanned
            String scannedLgToken = publicRedisTemplate.opsForValue().get(QRCODE_SCANNED_NAMESPACE + lgToken);
            if (scannedLgToken != null) {
                return new Result<Object>(BusinessCode.SCANNED, "scanning succeeded!");
            }

            return Result.success("login start state");
        } catch (Exception e) {
            LOGGER.error("check qrcode exception!", e);
        }
        return Result.fail("exception!");
    }


    @RequestMapping("/redisQueueSize")
    public Result redisQueueSize() {
        try {
            return Result.success(redisQueue.size());
        } catch (Exception e) {
            LOGGER.error("get redis queue size exception!", e);
        }
        return Result.fail("exception!");
    }
}
