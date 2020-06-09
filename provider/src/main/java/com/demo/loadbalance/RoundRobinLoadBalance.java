package com.demo.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.List;

/**
 * 轮询负载均衡实现
 * Created by chenxyz on 2020/5/29.
 */
public class RoundRobinLoadBalance implements LoadBalance {

    private static Integer index=0;

    public NodeInfo doselect(List<String> registryInfo) {
        synchronized (index){
            if (index>=registryInfo.size()){
                index=0;
            }
            String registry=registryInfo.get(index);
            index++;
            JSONObject registryjo=JSONObject.parseObject(registry);
            Collection values=registryjo.values();
            JSONObject node=new JSONObject();
            for (Object value:values){
                node=JSONObject.parseObject(value.toString());
            }
            JSONObject protocol=node.getJSONObject("protocol");
            NodeInfo nodeInfo=new NodeInfo();
            nodeInfo.setHost(protocol.getString("host")!=null?protocol.getString("host"):"");
            nodeInfo.setPort(protocol.getString("port")!=null?protocol.getString("port"):"");
            nodeInfo.setContextpath(protocol.getString("contextpath")!=null?protocol.getString("contextpath"):"");

            return nodeInfo;
        }
    }
}
