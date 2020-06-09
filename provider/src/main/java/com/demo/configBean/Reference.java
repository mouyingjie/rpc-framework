package com.demo.configBean;

import com.demo.advice.InvokeInvocationHandler;
import com.demo.cluster.Cluster;
import com.demo.cluster.FailFastClusterInvoke;
import com.demo.cluster.FailOverClusterInvoke;
import com.demo.cluster.FailSafeClusterInvoke;
import com.demo.invoke.Invoke;
import com.demo.invoke.impl.HttpInvoke;
import com.demo.invoke.impl.NettyInvoke;
import com.demo.invoke.impl.RmiInvoke;
import com.demo.loadbalance.LoadBalance;
import com.demo.loadbalance.RandomLoadBalance;
import com.demo.loadbalance.RoundRobinLoadBalance;
import com.demo.redis.RedisApi;
import com.demo.redis.RedisServerRegistry;
import com.demo.registry.BaseRegistryDelegate;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxyz on 2020/5/28.
 */
public class Reference extends BaseBeanConfig implements InitializingBean,
        FactoryBean,ApplicationContextAware {
    private  static  final long serialVersionUID=8645L;

    private  String id;
    private  String intf;
    private  String protocol;
    private  String loadbalance;
    private  String cluster;
    private  String retries;


    private  Invoke invoke;
    private  ApplicationContext applicationContext;

    private static Map<String,Invoke> invokeMap=new HashMap<String, Invoke>();
    private static Map<String,LoadBalance> loadBalances=new HashMap<String, LoadBalance>();
    private static Map<String,Cluster> clusterMap=new HashMap<String, Cluster>();

    private List<String> registryInfo=new ArrayList<String>();

    static{
        invokeMap.put("http",new HttpInvoke());
        invokeMap.put("rmi",new RmiInvoke());
        invokeMap.put("netty",new NettyInvoke());

        loadBalances.put("random",new RandomLoadBalance());
        loadBalances.put("roundrob",new RoundRobinLoadBalance());

        clusterMap.put("failover",new FailOverClusterInvoke());
        clusterMap.put("failfast",new FailFastClusterInvoke());
        clusterMap.put("failsafe",new FailSafeClusterInvoke());
    }

    public Reference() {
        System.out.println("reference construtctor");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {

        this.protocol = protocol;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {

        this.loadbalance = loadbalance;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //spring初始化调用，getBean()调用此方法
    //在此方法里面返回的是intf接口的代理
    public Object getObject() throws Exception {
        System.out.println("返回的代理对象");
        if (protocol!=null && !"".equals(protocol)){
            invoke=invokeMap.get(protocol);
        }else {
           Protocol protocolobj= applicationContext.getBean(Protocol.class);
           if (protocolobj!=null){
               invoke=invokeMap.get(protocolobj.getName());
           }else {
               invoke=invokeMap.get("http");
           }
        }
       return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[]{Class.forName(intf)},
        new InvokeInvocationHandler(invoke,this));

    }

    public Class<?> getObjectType() {
        if(intf!=null && !"".equals(intf)){
          try{
              return Class.forName(intf);
          }catch (ClassNotFoundException e){

          }
        }
        return null;
    }

    public boolean isSingleton() {
        return false;
    }

    public void afterPropertiesSet() throws Exception {
        registryInfo=BaseRegistryDelegate.getRegistry(id,applicationContext);
        System.out.println(registryInfo);

        //完成订阅
        //RedisApi.subscribe("channel"+id,new RedisServerRegistry());
    }

    public static Map<String, Cluster> getClusterMap() {
        return clusterMap;
    }

    public static void setClusterMap(Map<String, Cluster> clusterMap) {
        Reference.clusterMap = clusterMap;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public List<String> getRegistryInfo() {
        return registryInfo;
    }

    public void setRegistryInfo(List<String> registryInfo) {
        this.registryInfo = registryInfo;
    }

    public static Map<String, LoadBalance> getLoadBalances() {
        return loadBalances;
    }

    public static void setLoadBalances(Map<String, LoadBalance> loadBalances) {
        Reference.loadBalances = loadBalances;
    }
}
