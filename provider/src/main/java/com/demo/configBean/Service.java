package com.demo.configBean;

import com.demo.redis.RedisApi;
import com.demo.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Created by chenxyz on 2020/5/28.
 */
public class Service extends BaseBeanConfig implements InitializingBean,ApplicationContextAware{
    private  static  final long serialVersionUID=786329L;

    private String intf;
    private String ref;
    private String protocol;

    private static ApplicationContext applicationContext;

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        Service.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        BaseRegistryDelegate.registry(ref,applicationContext);

        //RedisApi.publish("channel"+ref,"");
    }
}
