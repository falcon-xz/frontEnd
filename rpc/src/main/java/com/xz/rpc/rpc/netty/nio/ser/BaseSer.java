package com.xz.rpc.rpc.netty.nio.ser;

import com.xz.rpc.rpc.info.po.BasePoNoSer;

/**
 * falcon -- 2016/11/22.
 */
public interface BaseSer {
    <T extends BasePoNoSer> byte[] serialize(T obj) ;
    <T extends BasePoNoSer> T deserialize(byte[] bytes, Class<T> targetClass) ;
}
