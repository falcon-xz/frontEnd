package com.xz.other.exception;

/**
 * falcon -- 2017/1/3.
 */
public class Demo2 {

    public static String demo2(String s) {
        String ss = null ;
        try {
            ss = Demo1.demo1(s) ;
        } catch (TestException e) {
            System.out.println("Demo2");
            System.out.println(e.getMessage());
        }
        return ss ;
    }
}
