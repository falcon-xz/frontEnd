package com.xz.rpc.rpc.netty;


import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.interfaces.Animal;
import com.xz.rpc.rpc.info.interfaces.impl.Dog;
import com.xz.rpc.rpc.netty.server.NettyRpcCenter;
import com.xz.rpc.rpc.netty.server.NettyServer;

/**
 * falcon -- 2016/11/22.
 */
public class NettyServerMain {
    public static void main(String[] args) {

        NettyRpcCenter.regist(Animal.class.getName(),new Dog());
        NettyServer nettyServer = new NettyServer(Config.IP,Config.PORT) ;
        nettyServer.start();

    }
}
