package com.xz.rpc.rpc.io.v2_IO.client;

import java.lang.reflect.Proxy;

/**
 * Created by root on 2016/10/27.
 */
public class RpcProxy {
    public static <T > T getProxy(Class<? > interfaceClass){
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[] {interfaceClass},new RpcInvocation(interfaceClass)) ;
    }
}
