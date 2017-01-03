package com.xz.other.exception;

/**
 * falcon -- 2017/1/3.
 */
public class Demo1 {

    public static String demo1(String s) throws TestException {
        if (s.equals("1")){
            return "" ;
        }else {
            System.out.println("Demo1");
            throw new TestException("fuck"+s) ;
        }
    }

}
