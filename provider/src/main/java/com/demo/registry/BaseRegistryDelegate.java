package com.demo.registry;

import com.demo.configBean.Registry;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by chenxyz on 2020/5/29.
 */
public class BaseRegistryDelegate {

    public static void registry(String ref, ApplicationContext applicationContext){
        Registry registry=applicationContext.getBean(Registry.class);
        String protocol=registry.getProtocol();
        BaseRegistry baseRegistry=Registry.getRegistryMap().get(protocol);
        baseRegistry.registry(ref,applicationContext);
    }

    public static List<String> getRegistry(String id,ApplicationContext applicationContext){

        Registry registry=applicationContext.getBean(Registry.class);
        String protocol=registry.getProtocol();
        BaseRegistry baseRegistry=Registry.getRegistryMap().get(protocol);
        return  baseRegistry.getRegistry(id,applicationContext);
    }
}
