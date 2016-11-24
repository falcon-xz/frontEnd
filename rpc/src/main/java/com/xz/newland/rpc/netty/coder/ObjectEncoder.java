package com.xz.newland.rpc.netty.coder;

import com.xz.newland.rpc.netty.transion.BasePo;
import com.xz.newland.rpc.netty.ser.BaseSer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * falcon -- 2016/11/21.
 */
public class ObjectEncoder extends MessageToByteEncoder{
    private Class<? extends BasePo> cz;
    private BaseSer baseSer ;

    public <T extends BaseSer> ObjectEncoder(Class<? extends BasePo> cz,T t) {
        this.cz = cz;
        baseSer = t ;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf byteBuf) throws Exception {
        System.out.println("-----ObjectEncoder------"+in.getClass().getName());
        if (cz.isInstance(in)) {
            byte[] data = baseSer.serialize((BasePo)in);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
