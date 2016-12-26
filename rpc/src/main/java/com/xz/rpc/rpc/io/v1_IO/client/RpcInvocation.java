package com.xz.rpc.rpc.io.v1_IO.client;

import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
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
        ObjectOutputStream oos = null ;
        ObjectInputStream ois = null ;
        Socket socket = null ;
        try {
            socket = new Socket(Config.IP, Config.PORT) ;
            oos = new ObjectOutputStream(socket.getOutputStream()) ;
            String uuid = UUID.randomUUID().toString() ;
            RequestSer requestSer = new RequestSer(uuid,interfaceClass,method.getName(),method.getParameterTypes(),params) ;
            oos.writeObject(requestSer);
            oos.flush();
            socket.shutdownOutput();

            ois = new ObjectInputStream(socket.getInputStream()) ;
            ResponseSer responseSer = (ResponseSer)ois.readObject() ;
            ois.close();
            ois = null ;
            oos.close();
            oos = null ;
            return responseSer.getObject();
        } finally {
            if (ois!=null){
                ois.close();
            }
            if (oos!=null){
                oos.close();
            }
            if (socket!=null){
                socket.close();
            }
        }
    }
}
