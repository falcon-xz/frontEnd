package com.xz.newland.rpc.netty.server;

import com.xz.newland.rpc.netty.transion.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * falcon -- 2016/11/22.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {
    public NettyServerHandler(){
        System.out.println("NettyServerHandler init");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request msg) throws Exception {
        System.out.println("------channelRead0--------");
        NettyDealRead nettyDealRead = new NettyDealRead(channelHandlerContext,msg) ;
        NettyRpcCenter.exec(nettyDealRead);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
