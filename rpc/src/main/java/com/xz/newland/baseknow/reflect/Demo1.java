package com.xz.newland.baseknow.reflect;

import com.xz.newland.baseknow.proxy.Animal;

import java.lang.reflect.Method;

/**
 * falcon -- 2016/12/2.
 */
public class Demo1 {
    public static void main(String[] args) {
        Class cz = Animal.class ;
        try {
            Method method = cz.getMethod("jiao") ;
            System.out.println(method.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
