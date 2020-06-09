package com.demo.cluster;

import com.demo.invoke.Invocation;
import com.demo.invoke.Invoke;

/**
 * 如果调用失败，直接忽略
 * Created by chenxyz on 2020/6/2.
 */
public class FailSafeClusterInvoke implements Cluster{

    public String invoke(Invocation invocation) throws Exception {
        Invoke invoke=invocation.getInvoke();
        try {
            return invoke.invoke(invocation);
        }catch (Exception e){
            e.printStackTrace();
            return "忽略";
        }
    }
}
