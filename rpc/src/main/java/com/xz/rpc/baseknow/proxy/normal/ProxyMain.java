package com.xz.rpc.baseknow.proxy.normal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * falcon -- 2016/11/13.
 */
public class ProxyMain {
    public static void main(String[] args) {
        Zoo zoo = (Zoo) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class<?>[] {Zoo.class}, new InvocationHandler() {
            private ZooDog dog = new ZooDog() ;
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(dog,args);
            }
        });
        System.out.println(zoo.jiao("fuck") );
    }
}
