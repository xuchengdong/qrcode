# 二维码
扫描二维码登录

## 系统模块说明
1. core：核心模块，提供核心技术解决方案
2. web：提供生成二维码登录业务接口

## 返回jsp、json、jsonp说明
* `@Controller` 默认返回jsp视图
* `@RestController` 默认返回`string`，结果为对象时返回`json`数据，请求包含`callback`参数时返回`jsonp`数据

### 案例
@Controller | @RestController
---|---
[http://qrcode.struggle.com/view/returnStr.do](http://qrcode.struggle.com/view/returnStr.do) |  [http://qrcode.struggle.com/rest/returnStr.do](http://qrcode.struggle.com/rest/returnStr.do) 
[http://qrcode.struggle.com/view/returnView.do](http://qrcode.struggle.com/view/returnView.do) |  [http://qrcode.struggle.com/rest/returnView.do](http://qrcode.struggle.com/rest/returnView.do) 
&nbsp;| [http://qrcode.struggle.com/rest/returnJsonOrJsonp.do](http://qrcode.struggle.com/rest/returnJsonOrJsonp.do) 
&nbsp;| [http://qrcode.struggle.com/rest/returnJsonOrJsonp.do?callback=fun](http://qrcode.struggle.com/rest/returnJsonOrJsonp.do?callback=fun)

## 业务接口说明
1. 获取二维码接口
[http://qrcode.struggle.com/qrcodelogin/generateQRCode4Login.do?callback=fun](http://qrcode.struggle.com/qrcodelogin/generateQRCode4Login.do?callback=fun)
```
fun({
    "code": 1000,   // 1000:成功,  9999:接口异常
    "message": null,
    "data": {
        "url": "http://ohhyhoixx.bkt.clouddn.com/71d4a7f0965f4f17a3ded13c79a14a616489409401646721232.png",
        "lgToken": "82de302436b1455e919c1603aedc6e8d",
        "cTime": 1480829655123,
        "expire": 60
    },
    "success": true
});
```

2. 校验二维码接口
[http://qrcode.struggle.com/qrcodelogin/qrcodeLoginCheck.do?lgToken=82de302436b1455e919c1603aedc6e8d&callback=fun](http://qrcode.struggle.com/qrcodelogin/qrcodeLoginCheck.do?lgToken=82de302436b1455e919c1603aedc6e8d&callback=fun)
```
fun({
    "code": 1000,   // 1000:待授权, 4001:二维码已失效, 2000:扫描成功, 2001:授权成功, 9999:接口异常
    "message": "login start state",
    "data": null,   // code=2001 时 data数据为自动登录的url，前端直接用此url跳转即可
    "success": true
});
```

3. 校验APP端是否已经登录接口
[http://m.struggle.com/api/qrcodelogin/login](http://m.struggle.com/api/qrcodelogin/login)
```
{
    "data": "u_10***0022",
    "responseCode": 1000,   // 1000：已登录 ，1005：未登陆
    "errorCode": 0,
    "errorMsg": ""
}
```

4. 授权登录接口
[http://m.struggle.com/api/qrcodelogin/authLogin?lgToken=82de302436b1455e919c1603aedc6e8d](http://m.struggle.com/api/qrcodelogin/authLogin?lgToken=82de302436b1455e919c1603aedc6e8d)
```
{
    "data": null,
    "responseCode": 1000,   // 1000：授权登录成功 ，1005：未登陆
    "errorCode": 0,
    "errorMsg": ""
}
```

5. 查询当前预生成二维码的数量
[http://qrcode.struggle.com/qrcodelogin/redisQueueSize.do?callback=fun](http://qrcode.struggle.com/qrcodelogin/redisQueueSize.do?callback=fun)
```
fun({
    "code": 1000,   // 1000:成功,  9999:接口异常
    "message": null,
    "data": 5,
    "success": true
});
```