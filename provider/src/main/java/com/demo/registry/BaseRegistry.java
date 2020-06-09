package com.demo.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by chenxyz on 2020/5/29.
 */
public interface BaseRegistry {

    public boolean registry(String param, ApplicationContext applicationContext);

    public List<String> getRegistry (String id,ApplicationContext applicationContext);
}
