package com.demo.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class ReferenceBeanDefinitionParse implements BeanDefinitionParser{

    private Class<?> beanClass;

    public ReferenceBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
        //spring会把beanclass实例化
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String intf=element.getAttribute("interface");
        String id=element.getAttribute("id");
        String protocol=element.getAttribute("protocol");
        String loadbalance=element.getAttribute("loadbalance");
        String cluster=element.getAttribute("cluster");
        String retries=element.getAttribute("retries");
        if (protocol == null || "".equals(protocol)) {
            throw new RuntimeException("Reference protocol 不能为空！");
        }

        if (id == null || "".equals(id)) {
            throw new RuntimeException("Reference ref 不能为空！");
        }

        if (intf == null || "".equals(intf)) {
            throw new RuntimeException("Reference interface 不能为空！");
        }

        if (loadbalance == null || "".equals(loadbalance)) {
            throw new RuntimeException("Reference loadbalance 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("intf",intf);
        beanDefinition.getPropertyValues().addPropertyValue("id",id);
        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("loadbalance",loadbalance);
        beanDefinition.getPropertyValues().addPropertyValue("retries",retries);
        beanDefinition.getPropertyValues().addPropertyValue("cluster",cluster);
        parserContext.getRegistry().registerBeanDefinition("Reference"+id,beanDefinition);
        return beanDefinition;
    }
}
