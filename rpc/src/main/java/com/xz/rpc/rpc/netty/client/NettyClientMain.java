package com.xz.rpc.rpc.netty.client;

import com.xz.rpc.rpc.netty.client.proxy.RpcProxy;
import com.xz.rpc.rpc.netty.server.interfaces.Animal;

/**
 * falcon -- 2016/11/23.
 */
public class NettyClientMain {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Animal animal = RpcProxy.getProxy(Animal.class) ;
            System.out.println(animal.eat("shit"));
            System.out.println(animal.jiao());
        }
    }
}
