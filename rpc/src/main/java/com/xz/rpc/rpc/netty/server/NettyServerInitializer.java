package com.xz.rpc.rpc.netty.server;

import com.xz.rpc.rpc.netty.coder.ObjectDecoder;
import com.xz.rpc.rpc.netty.coder.ObjectEncoder;
import com.xz.rpc.rpc.netty.ser.BaseSer;
import com.xz.rpc.rpc.netty.transion.Request;
import com.xz.rpc.rpc.netty.transion.Response;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * falcon -- 2016/11/22.
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private BaseSer bs ;
    private ByteToMessageDecoder btmd ;
    private MessageToByteEncoder mtbe ;
    public NettyServerInitializer(BaseSer bs){
        this.bs = bs ;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new ObjectDecoder(Request.class,bs))
                .addLast(new ObjectEncoder(Response.class,bs))
                .addLast(new NettyServerHandler());
    }
}
