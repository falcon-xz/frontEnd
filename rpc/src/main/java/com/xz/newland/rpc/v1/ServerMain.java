package com.xz.newland.rpc.v1;

import com.xz.newland.rpc.v1.server.interfaces.Animal;
import com.xz.newland.rpc.v1.server.rpc.RpcCenter;
import com.xz.newland.rpc.v1.server.interfaces.impl.Cat;
import com.xz.newland.rpc.v1.server.rpc.RpcServer;

/**
 * Created by xz on 2016/10/26.
 */
public class ServerMain {
    public static void main(String[] args) {
        RpcCenter rpcCenter = new RpcCenter() ;
        rpcCenter.regist(Animal.class.getName(),new Cat());
        RpcServer rpcServer = new RpcServer(rpcCenter) ;
        rpcServer.start(Config.PORT) ;
    }
}
