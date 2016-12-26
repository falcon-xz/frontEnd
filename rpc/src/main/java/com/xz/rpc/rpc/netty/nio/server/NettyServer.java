package com.xz.rpc.rpc.netty.nio.server;

import com.xz.rpc.rpc.netty.nio.ser.BaseSer;
import com.xz.rpc.rpc.netty.nio.ser.ProtostuffSer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * falcon -- 2016/11/22.
 */
public class NettyServer {

    private String ip ;
    private int port ;

    public NettyServer(String ip,int port ){
        this.ip = ip ;
        this.port = port ;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup() ;
        ServerBootstrap serverBootstrap = new ServerBootstrap() ;
        BaseSer bs = new ProtostuffSer() ;
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyServerInitializer(bs))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(ip,port)).sync(); // (7)

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
