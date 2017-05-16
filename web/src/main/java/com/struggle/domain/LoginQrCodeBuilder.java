package com.struggle.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author xuchengdongxcd@126.com on 2016/12/3.
 */
@Component
public class LoginQrCodeBuilder {

    @Value("${qiniu.bucket.domain}")
    private String bucketDomain;

    @Value("${qrcode.lgToken.expire}")
    private int lgTokenExpire;

    public LoginQrCode newLoginQrCode(String fileName, String token) {
        LoginQrCode loginQrCode = new LoginQrCode();
        loginQrCode.setUrl(bucketDomain + fileName);
        loginQrCode.setLgToken(token);
        loginQrCode.setExpire(lgTokenExpire);
        loginQrCode.setcTime(Calendar.getInstance().getTime());
        return loginQrCode;
    }
}
