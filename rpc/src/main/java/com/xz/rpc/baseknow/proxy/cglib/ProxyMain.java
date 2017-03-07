package com.xz.rpc.baseknow.proxy.cglib;

/**
 * falcon -- 2016/12/21.
 */
public class ProxyMain {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy() ;
        ZooCat zooCat = cglibProxy.getProxy(ZooCat.class) ;
        System.out.println("result:"+zooCat.getClass());
        System.out.println("result:"+zooCat.jiao("hello"));
    }
}
