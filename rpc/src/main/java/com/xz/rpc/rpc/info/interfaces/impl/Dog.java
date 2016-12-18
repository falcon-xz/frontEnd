package com.xz.rpc.rpc.info.interfaces.impl;

import com.xz.rpc.rpc.info.interfaces.Animal;

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
