package com.xz.newland.rpc.v1_IO.server.interfaces.impl;

import com.xz.newland.rpc.v1_IO.server.interfaces.Animal;

/**
 * Created by xz on 2016/10/26.
 */
public class Dog implements Animal {
    @Override
    public String jiao() {
        return "汪汪";
    }

    @Override
    public String feed(String s) {
        return getClass().getName()+"吃了"+s;
    }
}
