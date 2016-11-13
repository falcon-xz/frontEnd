package com.xz.newland.rpc.normal.v1_IO;

import com.xz.newland.rpc.normal.v1_IO.server.interfaces.Animal;
import com.xz.newland.rpc.normal.v1_IO.server.rpc.RpcCenter;
import com.xz.newland.rpc.normal.v1_IO.server.interfaces.impl.Cat;
import com.xz.newland.rpc.normal.v1_IO.server.rpc.RpcServer;

/**
 * Created by xz on 2016/10/26.
 */
class ServerMain {
    public static void main(String[] args) {
        RpcCenter rpcCenter = new RpcCenter() ;
        rpcCenter.regist(Animal.class.getName(),new Cat());
        RpcServer rpcServer = new RpcServer(rpcCenter) ;
        rpcServer.start(Config.PORT) ;
    }
}
