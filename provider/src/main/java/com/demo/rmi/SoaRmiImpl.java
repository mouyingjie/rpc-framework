package com.demo.rmi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.configBean.Service;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxyz on 2020/6/2.
 */
public class SoaRmiImpl extends UnicastRemoteObject implements SoaRmi {

    private static final long seriaVersionUID=93265742873L;

    public SoaRmiImpl() throws RemoteException {
    }

    public String invoke(String param) throws RemoteException {
        try {
            JSONObject requestParam=JSONObject.parseObject(param);
            String serviceId=requestParam.getString("serviceIs");
            String methodName=requestParam.getString("methodName");
            JSONArray paramTypes=requestParam.getJSONArray("paramTypes");
            JSONArray methodParamJa=requestParam.getJSONArray("methodParam");
            //反射的参数
            Object[] objs=null;
            if (methodParamJa!=null){
                int i=0;
                for (Object o: methodParamJa){
                    objs[i++]=o;
                }
            }

            //获取spring上下文
            ApplicationContext application= Service.getApplicationContext();
            //服务实例id
            Object serviceBean=application.getBean(serviceId);
            Method method=getMethod(serviceBean,methodName,paramTypes);
            if (method!=null){
                Object result=method.invoke(serviceBean,objs);
                return result.toString();

            }else {
                System.out.println("---no such method---");
                return  null;
            }

        }catch (Exception e){

            e.printStackTrace();
        }
        return  null;
    }


    public static Method getMethod (Object bean,String methodName,JSONArray paramTypes){
        Method[] methods=bean.getClass().getMethods();
        List<Method> retMethod=new ArrayList<Method>();
        for (Method method:methods){
            if (methodName.trim().equals(method.getName())){
                retMethod.add(method);
            }
        }

        if (retMethod.size()==1){
            return  retMethod.get(0);
        }

        boolean isSameSize=false;
        boolean isSameType=false;
        for (Method method:retMethod){
            Class[] types=method.getParameterTypes();
            if (types.length==paramTypes.size()){
                isSameSize=true;
            }
            if (!isSameSize){
                continue;
            }

            for (int i=0;i<types.length;i++){
                if (types[i].toString().contains(paramTypes.getString(i))){
                    isSameType=true;
                }else {
                    isSameType=false;

                }

                if (!isSameType){
                    continue ;
                }
            }

            if (isSameType){
                return  method;
            }
        }

        return  null;
    }
}
