package com.xz.rpc.rpc.netty.nio;

import com.xz.rpc.rpc.info.interfaces.Animal;
import com.xz.rpc.rpc.netty.nio.client.proxy.RpcProxy;

/**
 * falcon -- 2016/11/23.
 */
public class NettyClientMain {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Animal animal = RpcProxy.getProxy(Animal.class) ;
            System.out.println(animal.feed("shit"));
            System.out.println(animal.jiao());
        }
    }
}
