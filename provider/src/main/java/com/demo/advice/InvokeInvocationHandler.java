package com.demo.advice;

import com.demo.cluster.Cluster;
import com.demo.configBean.Reference;
import com.demo.invoke.Invocation;
import com.demo.invoke.Invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class InvokeInvocationHandler implements InvocationHandler{

    private Invoke invoke;
    private Reference reference;

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("获取到代理实例");
        //在这个invoke要调用多个远程provider
        Invocation invocation=new Invocation();
        invocation.setMethod(method);
        invocation.setObjs(args);
        invocation.setReference(reference);
        String result=invoke.invoke(invocation);
//        Cluster cluster=Reference.getClusterMap().get(reference.getCluster());
//        String result=cluster.invoke(invocation);
        return result;
    }
}
