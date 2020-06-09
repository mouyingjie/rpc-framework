package com.demo.spring.parse;

import com.demo.configBean.Protocol;
import com.demo.configBean.Reference;
import com.demo.configBean.Registry;
import com.demo.configBean.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class SOANamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("registry",new RegistryBeanDefinitionParse(Registry.class));
        this.registerBeanDefinitionParser("service",new ServiceBeanDefinitionParse(Service.class));
        this.registerBeanDefinitionParser("reference",new ReferenceBeanDefinitionParse(Reference.class));
        this.registerBeanDefinitionParser("protocol",new ProtocolBeanDefinitionParse(Protocol.class));
    }
}
