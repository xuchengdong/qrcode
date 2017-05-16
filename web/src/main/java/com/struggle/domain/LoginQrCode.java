package com.struggle.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuchengdongxcd@126.com on 2016/11/30.
 */
public class LoginQrCode implements Serializable{
    private static final long serialVersionUID = -4310056148510018432L;
    private String url;
    private String lgToken;
    private Date cTime;
    private int expire;

    public LoginQrCode() {
    }

    public LoginQrCode(String url, String lgToken, Date cTime, int expire) {
        this.url = url;
        this.lgToken = lgToken;
        this.cTime = cTime;
        this.expire = expire;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLgToken() {
        return lgToken;
    }

    public void setLgToken(String lgToken) {
        this.lgToken = lgToken;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
