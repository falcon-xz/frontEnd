package com.xz.other.construct;

/**
 * falcon -- 2016/12/16.
 */
public class Parent {
    private Po p ;
    public <T extends Po> Parent(T t) {
        this.p = t ;
        System.out.println("extends Po");
    }

    protected String getPo(){
        return p.getClass().getName();
    }
}
