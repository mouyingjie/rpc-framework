package com.demo.provideWeb.framework;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by chenxyz on 2020/6/1.
 */
public class FrameWorkTestServiceImpl implements  FrameWorkTestService, InitializingBean {

    public String sleep(String param) {
        String result="Provide->FrameworkTestServiceImpl->sleep->"+param;
        System.out.println(result);
        return result;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println(">>>>>>>>>>>InitializingBean>>>>>>>>>>>>>");
    }
}
