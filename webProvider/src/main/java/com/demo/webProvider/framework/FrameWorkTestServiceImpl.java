package com.demo.webProvider.framework;

/**
 * Created by chenxyz on 2020/6/1.
 */
public class FrameWorkTestServiceImpl implements  FrameWorkTestService {

    public String sleep(String param) {
        String result="Provide->FrameworkTestServiceImpl->sleep->"+param;
        System.out.println(result);
        return result;
    }
}
