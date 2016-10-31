package com.xz.newland.rpc.v1.server.interfaces.impl;

import com.xz.newland.rpc.v1.server.interfaces.Animal;

/**
 * Created by root on 2016/10/27.
 */
public class Cat implements Animal {
    @Override
    public String jiao() {
        return "喵喵";
    }

    @Override
    public String feed(String s) {
        return getClass().getName()+"吃了"+s;
    }
}
