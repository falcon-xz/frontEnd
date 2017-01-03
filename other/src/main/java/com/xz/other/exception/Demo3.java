package com.xz.other.exception;

/**
 * falcon -- 2017/1/3.
 */
public class Demo3 {

    public static String demo3(String s) {
        String ss = null ;
        try {
            ss = Demo2.demo2(s) ;
        } catch (Exception e) {
            System.out.println("Demo3");
            System.out.println(e.getMessage());
        }
        return ss ;
    }

    public static void main(String[] args) {
        Demo3.demo3("2");
    }
}
