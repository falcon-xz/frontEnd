package com.xz.newland.baseknow.netty.demo2;

import com.xz.newland.baseknow.serializable.protostuff.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * falcon -- 2016/11/21.
 */
public class ObjectDecoder extends ByteToMessageDecoder{
    private Class<?> cz ;
    public ObjectDecoder(Class<?> cz){
        this.cz = cz ;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        System.out.println("-----ObjectDecoder------");
        in.markReaderIndex();
        int dataLength = in.readInt();
        /*if (dataLength <= 0) {
            ctx.close();
        }*/
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = ProtostuffUtil.deserialize(data, cz);
        list.add(obj);
    }
}
