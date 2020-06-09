package com.demo.provideWeb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenxyz on 2020/6/1.
 */
@RestController
@RequestMapping
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "123";
    }
}
