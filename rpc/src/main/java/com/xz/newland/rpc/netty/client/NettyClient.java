package com.xz.newland.rpc.netty.client;

import com.xz.newland.rpc.netty.ser.BaseSer;
import com.xz.newland.rpc.netty.ser.IOSer;
import com.xz.newland.rpc.netty.ser.ProtostuffSer;
import com.xz.newland.rpc.netty.transion.Request;
import com.xz.newland.rpc.netty.transion.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.CountDownLatch;

/**
 * falcon -- 2016/11/22.
 */
public class NettyClient extends SimpleChannelInboundHandler<Response> {
    private String ip ;
    private int port ;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private Response response ;
    public NettyClient(String ip,int port){
        this.ip = ip ;
        this.port = port ;
    }
    public Response send(Request request){
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            BaseSer bs = new ProtostuffSer() ;
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer(bs,this));

            ChannelFuture channelFuture = b.connect(ip, port).sync();
            channelFuture.channel().writeAndFlush(request).sync() ;
            countDownLatch.await();
            if (response!=null){
                channelFuture.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
        return response ;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        try {
            this.response = response ;
            System.out.println("接收 response");
            countDownLatch.countDown();
            System.out.println("唤醒");
        } finally {
            ReferenceCountUtil.release(response);
            channelHandlerContext.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
