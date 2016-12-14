package com.xz.rpc.rpc.netty.server.interfaces.impl;

import com.xz.rpc.rpc.netty.server.interfaces.Animal;

/**
 * falcon -- 2016/11/23.
 */
public class Dog implements Animal {

    @Override
    public String eat(String food) {
        return "Dog eat "+food;
    }

    @Override
    public String jiao() {
        return this.getClass().getName();
    }
}
