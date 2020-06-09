package com.demo.invoke;

import com.demo.configBean.Reference;

import java.lang.reflect.Method;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class Invocation {

    private Method method;
    private  Object[] objs;
    private Reference reference;
    private Invoke invoke;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {

        this.objs = objs;
    }

    public Invoke getInvoke() {
        return invoke;
    }

    public void setInvoke(Invoke invoke) {
        this.invoke = invoke;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
