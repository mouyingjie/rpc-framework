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
public class ProtocolBeanDefinitionParse implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ProtocolBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
       //spring会把beanclass实例化
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String name=element.getAttribute("name");
        String host=element.getAttribute("host");
        String port=element.getAttribute("port");
        String contextpath=element.getAttribute("contextpath");
        if (name == null || "".equals(name)) {
            throw new RuntimeException("Protocol name 不能为空！");
        }

        if (host == null || "".equals(host)) {
            throw new RuntimeException("Protocol host 不能为空！");
        }

        if (port == null || "".equals(port)) {
            throw new RuntimeException("Protocol port 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("name",name);
        beanDefinition.getPropertyValues().addPropertyValue("host",host);
        beanDefinition.getPropertyValues().addPropertyValue("port",port);
        beanDefinition.getPropertyValues().addPropertyValue("contextpath",contextpath);
        parserContext.getRegistry().registerBeanDefinition("Protocol"+host+port,beanDefinition);
        return beanDefinition;
    }
}
