package com.xz.structureType.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * falcon -- 2016/11/13.
 */
public class ProxyMain {
    public static void main(String[] args) {
        Animal animal = (Animal) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class<?>[] {Animal.class}, new InvocationHandler() {
            private Dog dog = new Dog() ;
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(dog,args);
            }
        });
        System.out.println(animal.jiao("fuck") );
    }
}
