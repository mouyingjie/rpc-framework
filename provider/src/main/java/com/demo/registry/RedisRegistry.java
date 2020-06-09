package com.demo.registry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.configBean.Protocol;
import com.demo.configBean.Registry;
import com.demo.configBean.Service;
import com.demo.redis.RedisApi;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenxyz on 2020/5/29.
 */
public class RedisRegistry implements BaseRegistry {
    public boolean registry(String ref, ApplicationContext applicationContext) {
        try {

            Protocol protocol = applicationContext.getBean(Protocol.class);
            Map<String, Service> services = applicationContext.getBeansOfType(Service.class);

            Registry registry = applicationContext.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            for (Map.Entry<String, Service> entry : services.entrySet()) {
                if (entry.getValue().getRef().equals(ref)) {
                    JSONObject jo = new JSONObject();
                    jo.put("protocol", JSON.toJSONString(protocol));
                    jo.put("service", JSON.toJSONString(entry.getValue()));

                    JSONObject ipport = new JSONObject();
                    ipport.put(protocol.getHost() + ":" + protocol.getPort(), jo);

                    lpush(ipport, ref);
                }
            }

            return true;
        }catch (Exception e){}
        return  false;
    }

    private void lpush(JSONObject ipport,String key){
        if (RedisApi.exists(key)){
            Set<String> keys=ipport.keySet();
            String ipportStr="";
            //这个循环里面只会循环一次
            for (String ks:keys){
                ipportStr=ks;
            }

            boolean isold=false;

            List<String> registryInfo=RedisApi.lrange(key);
            List<String> newRegistryInfo=new ArrayList<String>();
            for (String node:registryInfo){
                JSONObject jo=JSON.parseObject(node);
                if (jo.containsKey(ipportStr)){
                    newRegistryInfo.add(ipport.toJSONString());
                    isold=true;
                }else {
                    newRegistryInfo.add(node);
                }
            }

            if (isold){
                if (newRegistryInfo.size()>0){
                    RedisApi.del(key);
                    String[] newRestr=new String[newRegistryInfo.size()];
                    for (int i=0;i<newRegistryInfo.size();i++){
                        newRestr[i]=newRegistryInfo.get(i);
                    }
                    //旧机器去重
                    RedisApi.lpush(key,newRestr);
                 }
            }else {
                //加入新启动的机器
                RedisApi.lpush(key,ipport.toJSONString());
            }
        }else {
            //第一次启动
            RedisApi.lpush(key,ipport.toJSONString());
        }
    }

    public List<String> getRegistry(String id, ApplicationContext applicationContext) {
       try {
           Registry registry = applicationContext.getBean(Registry.class);
           RedisApi.createJedisPool(registry.getAddress());
           if (RedisApi.exists(id)) {
               return RedisApi.lrange(id);
           }
       }catch (Exception e){}
       return  null;
    }
}
