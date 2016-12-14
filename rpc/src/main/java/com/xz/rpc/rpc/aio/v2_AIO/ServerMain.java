package com.xz.rpc.rpc.aio.v2_AIO;


import com.xz.rpc.rpc.aio.v2_AIO.server.interfaces.Animal;
import com.xz.rpc.rpc.aio.v2_AIO.server.interfaces.impl.Dog;
import com.xz.rpc.rpc.aio.v2_AIO.server.rpc.RpcCenter;
import com.xz.rpc.rpc.aio.v2_AIO.server.rpc.RpcServer;

import java.net.InetSocketAddress;

/**
 * Created by xz on 2016/10/26.
 */
public class ServerMain {
    public static void main(String[] args) {
        RpcCenter rpcCenter = new RpcCenter() ;
        rpcCenter.regist(Animal.class.getName(),new Dog());
        RpcServer rpcServer = new RpcServer(rpcCenter) ;
        rpcServer.start(new InetSocketAddress(Config.IP, Config.PORT)) ;
    }
}
