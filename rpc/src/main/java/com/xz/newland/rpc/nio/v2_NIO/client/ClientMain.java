package com.xz.newland.rpc.nio.v2_NIO.client;

import com.xz.newland.rpc.nio.v2_NIO.server.interfaces.Animal;
import com.xz.newland.rpc.nio.v2_NIO.client.proxy.RpcProxy;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain implements Runnable {

    @Override
    public void run() {
        Animal animal = RpcProxy.getProxy(Animal.class) ;
        System.out.println(animal.jiao());
        System.out.println(animal.feed("s"));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            new Thread(new ClientMain()).start();
        }
    }
}
