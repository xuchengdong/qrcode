package com.struggle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xuchengdongxcd@126.com on 2016/11/30.
 */
@Controller
@RequestMapping("/view")
public class JspViewController {

    @RequestMapping("/returnView")
    public ModelAndView returnView() {
        return new ModelAndView("hello");
    }

    @RequestMapping("/returnStr")
    public String returnStr() {
        return "hello";
    }
}
