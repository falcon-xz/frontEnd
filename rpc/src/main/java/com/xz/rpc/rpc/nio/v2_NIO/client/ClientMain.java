package com.xz.rpc.rpc.nio.v2_NIO.client;

import com.xz.rpc.rpc.nio.v2_NIO.server.interfaces.Animal;
import com.xz.rpc.rpc.nio.v2_NIO.client.proxy.RpcProxy;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {

    public static void main(String[] args) {
        Animal animal = RpcProxy.getProxy(Animal.class) ;
        long l1 = System.currentTimeMillis() ;
        animal.jiao();
        System.out.println("cost:"+(System.currentTimeMillis()-l1));
        animal.feed("s");
        System.out.println("cost:"+(System.currentTimeMillis()-l1));
        animal.jiao();
        System.out.println("cost:"+(System.currentTimeMillis()-l1));
        animal.feed("s");
        System.out.println("cost:"+(System.currentTimeMillis()-l1));
        animal.jiao();
        System.out.println("cost:"+(System.currentTimeMillis()-l1));
        animal.feed("s");
        System.out.println("cost:"+(System.currentTimeMillis()-l1));
    }
}
