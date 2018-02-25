package com.xz.rpc.baseknow.netty.demo2;

import com.xz.util.protostuff.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * falcon -- 2016/11/21.
 */
public class ObjectEncoder extends MessageToByteEncoder{

    private Class<?> cz;

    public ObjectEncoder(Class<?> cz) {
        this.cz = cz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf byteBuf) throws Exception {
        System.out.println("-----ObjectEncoder------"+in.getClass().getName());
        if (cz.isInstance(in)) {
            byte[] data = ProtostuffUtils.serialize(in);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
