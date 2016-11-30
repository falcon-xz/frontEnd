package com.xz.common.util.reflect;

import com.xz.common.utils.db.factory.DBCenter;

import java.lang.reflect.Method;

/**
 * falcon -- 2016/11/30.
 */
public class Test {
    public static void main(String[] args) {
        Class cz = DBCenter.class;
        Method[] methods = cz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

    }
}
