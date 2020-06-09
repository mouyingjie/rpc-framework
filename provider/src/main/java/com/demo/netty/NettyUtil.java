package com.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by chenxyz on 2020/6/2.
 */
public class NettyUtil {

    public static void startServer(String port) throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerInHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG,128);
            ChannelFuture f=b.bind(Integer.valueOf(port)).sync();
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static String senMsg(String host,String port,final  String sendmsg) throws Exception{
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        final StringBuffer resultmsg=new StringBuffer();
        try {
            Bootstrap b=new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyClinetHandler(resultmsg,sendmsg));
                }
            });

            ChannelFuture f=b.connect(host,Integer.valueOf(port)).channel().closeFuture().await();
            return resultmsg.toString();
        }finally {
            workerGroup.shutdownGracefully();
        }

    }
}
