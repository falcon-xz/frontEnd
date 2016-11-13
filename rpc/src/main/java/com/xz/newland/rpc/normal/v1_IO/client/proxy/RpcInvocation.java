package com.xz.newland.rpc.normal.v1_IO.client.proxy;

import com.xz.newland.rpc.normal.v1_IO.Config;
import com.xz.newland.rpc.normal.v1_IO.server.rpc.RpcInvocationReq;
import com.xz.newland.rpc.normal.v1_IO.server.rpc.RpcInvocationRes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

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
        Socket socket = new Socket(Config.IP,Config.PORT) ;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()) ;
        RpcInvocationReq rpcInvocationReq = new RpcInvocationReq(interfaceClass,method.getName(),method.getParameterTypes(),params) ;
        oos.writeObject(rpcInvocationReq);
        socket.shutdownOutput();

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()) ;
        RpcInvocationRes rpcInvocationRes = (RpcInvocationRes)ois.readObject() ;

        return rpcInvocationRes.getRet();
    }
}
