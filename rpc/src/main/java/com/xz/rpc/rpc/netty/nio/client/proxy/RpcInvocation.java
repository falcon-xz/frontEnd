package com.xz.rpc.rpc.netty.nio.client.proxy;

import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.RequestNoSer;
import com.xz.rpc.rpc.info.po.ResponseNoSer;
import com.xz.rpc.rpc.netty.nio.client.NettyClient;

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
        RequestNoSer requestNoSer = new RequestNoSer() ;
        requestNoSer.setRequestId(UUID.randomUUID().toString());
        requestNoSer.setInterFace(interfaceClass);
        requestNoSer.setMethodName(method.getName());
        requestNoSer.setParamsType(method.getParameterTypes());
        requestNoSer.setParams(params);

        NettyClient nettyClient = new NettyClient(Config.IP,Config.PORT) ;
        ResponseNoSer responseNoSer = nettyClient.send(requestNoSer) ;
        Object ret = responseNoSer.getObject() ;
        if (ret instanceof Exception){
            throw (Exception)ret ;
        }
        return ret;
    }
}
