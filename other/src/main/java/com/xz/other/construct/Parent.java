package com.xz.other.construct;

/**
 * falcon -- 2016/12/16.
 */
public class Parent {
    private Po p ;
    public <T extends Po> Parent(T t) {
        this.p = p ;
        System.out.println("extends Po");
    }

    public Parent() {
        System.out.println("no extends Po");
    }
    public Parent(String s) {
        System.out.println("no extends Po2");
    }
}
