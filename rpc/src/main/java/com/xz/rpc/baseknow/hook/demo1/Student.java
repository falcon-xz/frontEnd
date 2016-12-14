package com.xz.rpc.baseknow.hook.demo1;

/**
 * falcon -- 2016/11/17.
 */
public class Student {

    public String answer(Teacher teacher){
        return teacher.getClass().getName()+"Student" ;
    }
}
