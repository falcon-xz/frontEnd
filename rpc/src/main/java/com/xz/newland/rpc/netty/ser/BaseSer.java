package com.xz.newland.rpc.netty.ser;

import com.xz.newland.rpc.netty.transion.BasePo;

/**
 * falcon -- 2016/11/22.
 */
public interface BaseSer {
    <T extends BasePo> byte[] serialize(T obj) ;
    <T extends BasePo> T deserialize(byte[] bytes, Class<T> targetClass) ;
}
