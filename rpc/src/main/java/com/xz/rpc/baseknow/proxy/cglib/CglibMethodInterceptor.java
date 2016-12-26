package com.xz.rpc.baseknow.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * falcon -- 2016/12/21.
 */
public class CglibMethodInterceptor implements MethodInterceptor{
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(o,objects) ;
        return result;
    }
}
