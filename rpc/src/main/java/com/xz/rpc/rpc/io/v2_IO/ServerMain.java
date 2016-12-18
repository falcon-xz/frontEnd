package com.xz.rpc.rpc.io.v2_IO;

import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.RpcCenter;
import com.xz.rpc.rpc.info.interfaces.Animal;
import com.xz.rpc.rpc.info.interfaces.impl.Cat;
import com.xz.rpc.rpc.io.v2_IO.server.RpcServer;

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
