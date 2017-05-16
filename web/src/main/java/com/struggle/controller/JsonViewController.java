package com.struggle.controller;

import com.struggle.domain.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xuchengdongxcd@126.com on 2016/11/30.
 */
@RestController
@RequestMapping("/rest")
public class JsonViewController {

    @RequestMapping("/returnJsonOrJsonp")
    public Result returnJsonOrJsonp() {
        return Result.success();
    }

    @RequestMapping("/returnView")
    public ModelAndView returnView() {
        return new ModelAndView("hello");
    }

    @RequestMapping("/returnStr")
    public String returnStr() {
        return "hello";
    }
}
