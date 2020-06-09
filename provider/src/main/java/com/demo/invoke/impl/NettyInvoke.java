package com.demo.invoke.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.configBean.Reference;
import com.demo.invoke.Invocation;
import com.demo.invoke.Invoke;
import com.demo.loadbalance.LoadBalance;
import com.demo.loadbalance.NodeInfo;
import com.demo.netty.NettyUtil;

import java.util.List;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class NettyInvoke implements Invoke{

    public String invoke(Invocation invocation) throws Exception{
        try {
            List<String> registryInfo=invocation.getReference().getRegistryInfo();
            //负载均衡算法
            String loadbalance=invocation.getReference().getLoadbalance();
            Reference reference=invocation.getReference();
            LoadBalance loadBalanceBean=Reference.getLoadBalances().get(reference.getLoadbalance());
            NodeInfo nodeInfo=loadBalanceBean.doselect(registryInfo);

            JSONObject sendparam=new JSONObject();
            sendparam.put("methodName",invocation.getMethod().getName());
            sendparam.put("methodParams",invocation.getObjs());
            sendparam.put("serviceId",reference.getId());
            sendparam.put("paramTypes",invocation.getMethod().getParameterTypes());


            return  NettyUtil.senMsg(nodeInfo.getHost(),nodeInfo.getPort(),sendparam.toJSONString());
        }catch (Exception e){
           throw  e;
        }
    }
}
