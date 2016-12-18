package com.xz.rpc.rpc.netty.client;

import com.xz.rpc.rpc.info.po.RequestNoSer;
import com.xz.rpc.rpc.info.po.ResponseNoSer;
import com.xz.rpc.rpc.netty.coder.ObjectDecoder;
import com.xz.rpc.rpc.netty.coder.ObjectEncoder;
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
    private SimpleChannelInboundHandler<ResponseNoSer> nettyClient ;
    public NettyClientInitializer(BaseSer bs, SimpleChannelInboundHandler<ResponseNoSer> nettyClient){
        this.bs = bs ;
        this.nettyClient = nettyClient ;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        BaseSer bs = new ProtostuffSer() ;
        socketChannel.pipeline()
                .addLast(new ObjectEncoder(RequestNoSer.class,bs))
                .addLast(new ObjectDecoder(ResponseNoSer.class,bs))
                .addLast(nettyClient);
    }
}
