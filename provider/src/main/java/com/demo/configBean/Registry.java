package com.demo.configBean;

import com.demo.registry.BaseRegistry;
import com.demo.registry.BaseRegistryDelegate;
import com.demo.registry.RedisRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class Registry extends BaseBeanConfig implements InitializingBean,ApplicationContextAware{

    private  static  final long serialVersionUID=6239L;

    private  String protocol;
    private  String address;

    private static ApplicationContext applicationContext;

    private static Map<String,BaseRegistry>  registryMap=new HashMap<String, BaseRegistry>();

    static {
        registryMap.put("redis",new RedisRegistry());
    }

    public static Map<String, BaseRegistry> getRegistryMap() {
        return registryMap;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public  void setApplicationContext(ApplicationContext applicationContext) {
        Registry.applicationContext = applicationContext;
    }

    public static void setRegistryMap(Map<String, BaseRegistry> registryMap) {
        Registry.registryMap = registryMap;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void afterPropertiesSet() throws Exception {

    }


}
