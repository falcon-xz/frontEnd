package com.xz.rpc.rpc.aio.v1_AIO.client;


import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.BasePoSer;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by root on 2016/10/27.
 */
class RpcInvocation implements InvocationHandler {
    private Class interfaceClass;
    private AsynchronousSocketChannel socketChannel ;

    RpcInvocation(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
        try {
            socketChannel = AsynchronousSocketChannel.open() ;
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY,true);
            socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR,true);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        String uuid = UUID.randomUUID().toString() ;
        RequestSer requestSer = new RequestSer(uuid,interfaceClass,method.getName(),method.getParameterTypes(),params) ;
        ClientWork clientWork = new ClientWork(requestSer,socketChannel) ;
        ClientMaster clientMaster = ClientMaster.getInstance() ;
        clientMaster.put(clientWork);
        BasePoSer basePoSer = null ;
        boolean hasResult = true ;
        do {
            basePoSer=clientMaster.getResult(requestSer.getRequestId()) ;
            if (basePoSer!=null && basePoSer instanceof ResponseSer){
                hasResult = false ;
            }
        }while(hasResult) ;
        ResponseSer responseSer = (ResponseSer)basePoSer ;
        clientMaster.removeResult(responseSer);
        System.out.println(responseSer.getRequestId().equals(uuid));
        return responseSer.getObject() ;
    }


}
