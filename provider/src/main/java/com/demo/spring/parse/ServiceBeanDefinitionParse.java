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
public class ServiceBeanDefinitionParse implements BeanDefinitionParser {

    private Class beanClass;

    public ServiceBeanDefinitionParse(Class beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
       //spring会把beanclass实例化
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String intf=element.getAttribute("interface");
        String ref=element.getAttribute("ref");
        String protocol=element.getAttribute("protocol");
        String id=element.getAttribute("id");
       /* if (id == null || "".equals(id)) {
            throw new RuntimeException("Service id 不能为空！");
        }*/

        if (ref == null || "".equals(ref)) {
            throw new RuntimeException("Service ref 不能为空！");
        }

        if (intf == null || "".equals(intf)) {
            throw new RuntimeException("Service interface 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("intf",intf);
        beanDefinition.getPropertyValues().addPropertyValue("ref",ref);
        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("id",id);
        parserContext.getRegistry().registerBeanDefinition("Service"+ref,beanDefinition);
        return beanDefinition;
    }
}
