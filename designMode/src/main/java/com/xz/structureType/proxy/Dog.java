package com.xz.structureType.proxy;

/**
 * falcon -- 2016/11/13.
 */
class Dog implements Animal{

    @Override
    public String jiao(String s ) {
        return "dog 旺:"+s;
    }
}
