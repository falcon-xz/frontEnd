package com.xz.rpc.rpc.netty.client;

import com.xz.rpc.rpc.netty.coder.ObjectDecoder;
import com.xz.rpc.rpc.netty.coder.ObjectEncoder;
import com.xz.rpc.rpc.netty.transion.Request;
import com.xz.rpc.rpc.netty.transion.Response;
import com.xz.rpc.rpc.netty.ser.BaseSer;
import com.xz.rpc.rpc.netty.ser.ProtostuffSer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

/**
 * falcon -- 2016/11/22.
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    private BaseSer bs  ;
    private SimpleChannelInboundHandler<Response> nettyClient ;
    public NettyClientInitializer(BaseSer bs, SimpleChannelInboundHandler<Response> nettyClient){
        this.bs = bs ;
        this.nettyClient = nettyClient ;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        BaseSer bs = new ProtostuffSer() ;
        socketChannel.pipeline()
                .addLast(new ObjectEncoder(Request.class,bs))
                .addLast(new ObjectDecoder(Response.class,bs))
                .addLast(nettyClient);
    }
}
