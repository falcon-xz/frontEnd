package com.xz.rpc.rpc.io.client;

import com.xz.rpc.rpc.io.client.proxy.RpcProxy;
import com.xz.rpc.rpc.io.server.interfaces.Animal;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {
    public static void main(String[] args) {
        Animal animal = RpcProxy.getProxy(Animal.class) ;
        long l1 = System.currentTimeMillis() ;
        animal.jiao();
        System.out.println(System.currentTimeMillis()-l1);
        animal.feed("s");
        System.out.println(System.currentTimeMillis()-l1);
        animal.jiao();
        System.out.println(System.currentTimeMillis()-l1);
        animal.feed("s");
        System.out.println(System.currentTimeMillis()-l1);
        animal.jiao();
        System.out.println(System.currentTimeMillis()-l1);
        animal.feed("s");
        System.out.println(System.currentTimeMillis()-l1);
    }
}
