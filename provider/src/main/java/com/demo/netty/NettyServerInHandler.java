package com.demo.netty;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.configBean.Service;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxyz on 2020/6/2.
 */
public class NettyServerInHandler extends ChannelInboundHandlerAdapter {
    public NettyServerInHandler() {
        super();
    }

    @Override
    public boolean isSharable() {
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * netty客戶端有消息過來就會調這個方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result=(ByteBuf) msg;
        byte[] result1=new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr=new String(result1);
        System.out.println(resultStr);

        result.release();
        String response=invokeService(resultStr);
        ByteBuf encoded=ctx.alloc().buffer(4*response.length());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(encoded);
        ctx.close();

    }

    public String invokeService(String param) throws RemoteException {
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

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.deregister(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
