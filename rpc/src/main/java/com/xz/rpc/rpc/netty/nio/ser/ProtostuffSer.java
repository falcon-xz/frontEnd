package com.xz.rpc.rpc.netty.nio.ser;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.xz.rpc.rpc.info.po.BasePoNoSer;
import com.xz.util.protostuff.ProtostuffUtils;

/**
 * falcon -- 2016/11/22.
 */
public class ProtostuffSer implements BaseSer{

    @Override
    public <T extends BasePoNoSer> byte[] serialize(T obj) {
        return ProtostuffUtils.serialize(obj) ;
    }

    @Override
    public <T extends BasePoNoSer> T deserialize(byte[] bytes, Class<T> targetClass) {
        return ProtostuffUtils.deserialize(bytes,targetClass) ;
    }
}
