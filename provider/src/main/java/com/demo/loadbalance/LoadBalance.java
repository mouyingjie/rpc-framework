package com.demo.loadbalance;

import java.util.List;

/**
 * Created by chenxyz on 2020/5/29.
 */
public interface LoadBalance {
    public NodeInfo doselect(List<String> registryInfo);
}
