package com.xz.newland.rpc.netty.client.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by root on 2016/10/27.
 */
public class RpcProxy {
    public static <T > T getProxy(Class<? > interfaceClass){
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[] {interfaceClass},new RpcInvocation(interfaceClass)) ;
    }
}
