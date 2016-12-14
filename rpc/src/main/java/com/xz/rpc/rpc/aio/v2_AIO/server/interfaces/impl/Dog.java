package com.xz.rpc.rpc.aio.v2_AIO.server.interfaces.impl;

import com.xz.rpc.rpc.aio.v2_AIO.server.interfaces.Animal;

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
