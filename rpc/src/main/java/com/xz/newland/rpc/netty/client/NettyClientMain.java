package com.xz.newland.rpc.netty.client;

import com.xz.newland.rpc.netty.client.proxy.RpcProxy;
import com.xz.newland.rpc.netty.server.interfaces.Animal;

/**
 * falcon -- 2016/11/23.
 */
public class NettyClientMain {
    public static void main(String[] args) {
        Animal animal = RpcProxy.getProxy(Animal.class) ;
        System.out.println(animal.eat("shit"));
        System.out.println(animal.jiao());
    }
}
