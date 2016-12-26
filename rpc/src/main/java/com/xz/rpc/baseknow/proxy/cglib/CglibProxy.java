package com.xz.rpc.baseknow.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * falcon -- 2016/12/21.
 */
public class CglibProxy {
    private Enhancer enhancer ;
    private CglibMethodInterceptor cglibMethodInterceptor ;

    public CglibProxy() {
        enhancer = new Enhancer() ;
        cglibMethodInterceptor = new CglibMethodInterceptor() ;
    }

    public <T> T getProxy(Class cz){
        enhancer.setSuperclass(cz);
        enhancer.setCallback(cglibMethodInterceptor);
        return (T)enhancer.create() ;
    }
}
