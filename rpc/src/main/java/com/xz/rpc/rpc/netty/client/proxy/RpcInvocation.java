package com.xz.rpc.rpc.netty.client.proxy;

import com.xz.rpc.rpc.netty.client.NettyClient;
import com.xz.rpc.rpc.netty.transion.Request;
import com.xz.rpc.rpc.netty.transion.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by root on 2016/10/27.
 */
class RpcInvocation implements InvocationHandler {
    private Class interfaceClass ;
    RpcInvocation (Class interfaceClass){
        this.interfaceClass = interfaceClass ;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        Request request = new Request() ;
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterFace(interfaceClass);
        request.setMethodName(method.getName());
        request.setParamsType(method.getParameterTypes());
        request.setParams(params);

        NettyClient nettyClient = new NettyClient("127.0.0.1",8080) ;
        Response response = nettyClient.send(request) ;
        Object ret = response.getObject() ;
        if (ret instanceof Exception){
            throw (Exception)ret ;
        }
        return ret;
    }
}
