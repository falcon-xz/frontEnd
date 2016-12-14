package com.xz.rpc.rpc.netty.server;


import com.xz.rpc.rpc.netty.server.interfaces.Animal;
import com.xz.rpc.rpc.netty.server.interfaces.impl.Dog;

/**
 * falcon -- 2016/11/22.
 */
public class NettyServerMain {
    public static void main(String[] args) {

        NettyRpcCenter.regist(Animal.class.getName(),new Dog());
        NettyServer nettyServer = new NettyServer("127.0.0.1",8080) ;
        nettyServer.start();

    }
}
