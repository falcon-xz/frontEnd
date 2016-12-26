package com.xz.rpc.rpc.netty.nio.server;

import com.xz.rpc.rpc.info.po.RequestNoSer;
import com.xz.rpc.rpc.info.po.ResponseNoSer;
import com.xz.rpc.rpc.netty.nio.coder.ObjectDecoder;
import com.xz.rpc.rpc.netty.nio.coder.ObjectEncoder;
import com.xz.rpc.rpc.netty.nio.ser.BaseSer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * falcon -- 2016/11/22.
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private BaseSer bs ;

    public NettyServerInitializer(BaseSer bs){
        this.bs = bs ;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new ObjectDecoder(RequestNoSer.class,bs))
                .addLast(new ObjectEncoder(ResponseNoSer.class,bs))
                .addLast(new NettyServerHandler());
    }
}
