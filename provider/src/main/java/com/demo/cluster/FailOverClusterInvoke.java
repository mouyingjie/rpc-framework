package com.demo.cluster;

import com.demo.invoke.Invocation;
import com.demo.invoke.Invoke;

/**
 * 如果调用失败，自动切换到集群其他节点
 * Created by chenxyz on 2020/6/2.
 */
public class FailOverClusterInvoke implements Cluster{

    public String invoke(Invocation invocation) throws Exception {
        String retries=invocation.getReference().getRetries();
        Integer retrint=Integer.parseInt(retries);
        for (int i=0;i<retrint;i++){
            try {
                Invoke invoke=invocation.getInvoke();
                String result=invoke.invoke(invocation);
                return result;
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
        throw new RuntimeException("retries" + retries + "全部失败！！！！");
    }
}
