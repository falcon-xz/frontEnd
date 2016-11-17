package com.xz.newland.baseknow.hook.demo1;

/**
 * falcon -- 2016/11/17.
 */
public class MyHookImpl1 implements MyHook {

    private int count = 0 ;

    @Override
    public void before() {
        count++ ;
    }

    @Override
    public int after() {
        return count ;
    }
}
