package com.xz.rpc.rpc.aio.v2_AIO.client;

import com.xz.rpc.rpc.aio.v2_AIO.client.proxy.RpcProxy;
import com.xz.rpc.rpc.aio.v2_AIO.server.interfaces.Animal;

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
