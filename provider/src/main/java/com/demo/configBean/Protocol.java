package com.demo.configBean;

import com.demo.netty.NettyUtil;
import com.demo.rmi.RmiUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class Protocol extends BaseBeanConfig implements InitializingBean,
        ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{

    private  static  final long serialVersionUID=234123L;

    private String name;
    private String host;
    private String port;
    private String contextpath;

    private static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public  void setApplicationContext(ApplicationContext applicationContext) {
        Protocol.applicationContext = applicationContext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {

        this.contextpath = contextpath;
    }

    public void afterPropertiesSet() throws Exception {
        if (name.equalsIgnoreCase("rmi")){
            RmiUtil rmiUtil=new RmiUtil();
            rmiUtil.startRmiServer(host,port,"jacksoarmi");
        }
        if (name.equalsIgnoreCase("netty")){
            try {
                NettyUtil.startServer(port);
            }catch (Exception e){
                throw e;
            }
        }
    }

    /**
     * spring启动完成触发的事件
     * @param event
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!ContextRefreshedEvent.class.getName().equals(event.getClass().getName()))
            return;

      if (!"netty".equalsIgnoreCase(name)){
          return;
      }
      new Thread(new Runnable() {
          public void run() {
              try {
                  NettyUtil.startServer(port);
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
      }).start();


    }
}
