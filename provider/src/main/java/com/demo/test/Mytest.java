package com.demo.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class Mytest {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("mytest.xml");
//        UserService userService=context.getBean(UserService.class);
//        System.out.println(userService.eat("樱桃"));
    }
}
