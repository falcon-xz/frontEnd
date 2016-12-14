package com.xz.rpc.baseknow.hook.demo1;

/**
 * falcon -- 2016/11/17.
 */
public class Main {
    public static void main(String[] args) {
        Teacher teacher = new Teacher() ;
        teacher.addHook(new MyHookImpl2());
        System.out.println(teacher.askStudent());
        System.out.println(teacher.askStudent());
        System.out.println(teacher.getNum());
    }
}
