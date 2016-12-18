package com.xz.rpc.rpc.netty.client;

import com.xz.rpc.rpc.info.po.RequestNoSer;
import com.xz.rpc.rpc.info.po.ResponseNoSer;
import com.xz.rpc.rpc.netty.ser.BaseSer;
import com.xz.rpc.rpc.netty.ser.ProtostuffSer;
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
public class NettyClient extends SimpleChannelInboundHandler<ResponseNoSer> {
    private String ip ;
    private int port ;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ResponseNoSer responseNoSer ;
    public NettyClient(String ip,int port){
        this.ip = ip ;
        this.port = port ;
    }
    public ResponseNoSer send(RequestNoSer requestNoSer){
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            BaseSer bs = new ProtostuffSer() ;
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer(bs,this));

            ChannelFuture channelFuture = b.connect(ip, port).sync();
            channelFuture.channel().writeAndFlush(requestNoSer).sync() ;
            countDownLatch.await();
            if (responseNoSer!=null){
                channelFuture.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
        return responseNoSer ;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseNoSer responseNoSer) throws Exception {
        try {
            this.responseNoSer = responseNoSer ;
            System.out.println("接收 response");
            countDownLatch.countDown();
            System.out.println("唤醒");
        } finally {
            ReferenceCountUtil.release(responseNoSer);
            channelHandlerContext.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
