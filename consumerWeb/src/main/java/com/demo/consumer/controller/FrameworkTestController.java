package com.demo.consumer.controller;

import com.demo.provideWeb.framework.FrameWorkTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenxyz on 2020/6/1.
 */
@Controller
@RequestMapping("/frame")
public class FrameworkTestController {

    @Autowired
    FrameWorkTestService frameWorkTestService;

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return frameWorkTestService.sleep("hello world!");
    }
}
