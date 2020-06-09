package com.demo.cluster;

import com.demo.invoke.Invocation;

/**
 * Created by chenxyz on 2020/6/2.
 */
public interface Cluster {

    public String invoke(Invocation invocation) throws Exception;
}
