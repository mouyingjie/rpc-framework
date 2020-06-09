package com.demo.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by chenxyz on 2020/5/28.
 * registry标签解析成类
 */
public class RegistryBeanDefinitionParse implements BeanDefinitionParser {

    private Class<?> beanClass;

    public RegistryBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
       //spring会把beanclass实例化
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String protocol=element.getAttribute("protocol");
        String address=element.getAttribute("address");
        if (protocol == null || "".equals(protocol)) {
            throw new RuntimeException("Registry protocol 不能为空！");
        }

        if (address == null || "".equals(address)) {
            throw new RuntimeException("Registry address 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address",address);
        parserContext.getRegistry().registerBeanDefinition("Registry"+address,beanDefinition);

        return beanDefinition;
    }
}
