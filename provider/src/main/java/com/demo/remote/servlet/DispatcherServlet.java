package com.demo.remote.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.configBean.Service;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 给生产者接受请求用的servlet，只有采用HTTP协议才能调得到
 * Created by chenxyz on 2020/6/1.
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            JSONObject requestParam=httpProcess(req,res);
            String serviceId=requestParam.getString("serviceId");
            String methodName=requestParam.getString("methodName");
            JSONArray paramTypes=requestParam.getJSONArray("paramTypes");
            JSONArray methodParamJa=requestParam.getJSONArray("methodParams");
            //反射的参数
            Object[] objs=null;
            if (methodParamJa!=null){
                objs=new Object[methodParamJa.size()];
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
               PrintWriter pw=res.getWriter();
               pw.write(result.toString());

            }else {
                PrintWriter pw=res.getWriter();
                pw.write("---no such method---");
            }

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public static JSONObject httpProcess(ServletRequest req, ServletResponse res) throws IOException{
        StringBuffer sb=new StringBuffer();
        InputStream is=req.getInputStream();

        BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
        String s="";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        if (sb.toString().length() <= 0) {
            return null;
        }
        else {
            return JSONObject.parseObject(sb.toString());
        }

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
