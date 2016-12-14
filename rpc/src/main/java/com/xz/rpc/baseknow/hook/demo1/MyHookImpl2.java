package com.xz.rpc.baseknow.hook.demo1;

/**
 * falcon -- 2016/11/17.
 */
public class MyHookImpl2 implements MyHook{
    private int count = 0 ;

    @Override
    public void before() {
        count++ ;
        count++ ;
    }

    @Override
    public int after() {
        return count ;
    }
}
