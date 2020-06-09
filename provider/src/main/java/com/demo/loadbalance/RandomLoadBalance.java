package com.demo.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by chenxyz on 2020/5/29.
 */
public class RandomLoadBalance implements LoadBalance {
    public NodeInfo doselect(List<String> registryInfo) {
        Random random=new Random();
        int index=random.nextInt(registryInfo.size());
        String registry=registryInfo.get(index);
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
