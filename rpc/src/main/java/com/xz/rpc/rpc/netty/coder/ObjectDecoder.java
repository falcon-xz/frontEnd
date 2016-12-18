package com.xz.rpc.rpc.netty.coder;

import com.xz.rpc.rpc.info.po.BasePoNoSer;
import com.xz.rpc.rpc.netty.ser.BaseSer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * falcon -- 2016/11/21.
 */
public class ObjectDecoder extends ByteToMessageDecoder{
    private Class<? extends BasePoNoSer> cz ;
    private BaseSer baseSer ;
    public <T extends BaseSer> ObjectDecoder(Class<? extends BasePoNoSer> cz,T t){
        this.cz = cz ;
        baseSer = t ;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = baseSer.deserialize(data, cz);
        list.add(obj);
    }
}
