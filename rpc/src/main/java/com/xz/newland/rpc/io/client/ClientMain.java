package com.xz.newland.rpc.io.client;

import com.xz.newland.rpc.io.client.proxy.RpcProxy;
import com.xz.newland.rpc.io.server.interfaces.Animal;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {
    public static void main(String[] args) {
        Animal animal = RpcProxy.getProxy(Animal.class) ;
        System.out.println(animal.jiao());
        System.out.println(animal.feed("s"));
    }
}
