package com.struggle.qiniu.demo;

import com.qiniu.util.Auth;

public class DownloadDemo {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "82aAJmdJl1Te1MVlUhaIIvJ2W8NgDRuR9_PfscrT";
    String SECRET_KEY = "DGEhTxPgIzCpw1sYzJub5iJbXNQ5wnzqoBuQnvMR";
    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //构造私有空间的需要生成的下载的链接
    String URL = "http://ohhyhoixx.bkt.clouddn.com/docker.png";

    public static void main(String args[]) {
        new DownloadDemo().download();
    }

    public void download() {
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL, 3600);
        System.out.println(downloadRUL);
    }
}
