package com.xz.newland.rpc.netty.client.proxy;

import com.xz.newland.rpc.netty.client.NettyClient;
import com.xz.newland.rpc.netty.client.NettyClientInitializer;
import com.xz.newland.rpc.netty.ser.BaseSer;
import com.xz.newland.rpc.netty.ser.ProtostuffSer;
import com.xz.newland.rpc.netty.transion.Request;
import com.xz.newland.rpc.netty.transion.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

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
