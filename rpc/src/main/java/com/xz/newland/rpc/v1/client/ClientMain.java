package com.xz.newland.rpc.v1.client;

import com.xz.newland.rpc.v1.client.proxy.RpcProxy;
import com.xz.newland.rpc.v1.server.interfaces.Animal;

/**
 * Created by root on 2016/10/27.
 */
public class ClientMain {
    public static void main(String[] args) {
        Animal animal = RpcProxy.getProxy(Animal.class) ;
        System.out.println(animal.jiao());
        System.out.println(animal.feed("s"));
    }
}
