package com.xz.other.string;

/**
 * falcon -- 2016/12/21.
 */
public class StringTest {
    public static void test1(){
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < 50000 ; i++) {
            String s1 = "a" ;
            String s2 = "b" ;
            String s3 = "c" ;
            String s4 = "d" ;
            String s5 = "e" ;
            String s6 = s1+s2+s3+s4+s5 ;
        }
        long l2 = System.currentTimeMillis() ;
        System.out.println(l2-l1);
    }
    public static void test2(){
        long l1 = System.currentTimeMillis() ;
        StringBuilder sb = null;
        for (int i = 0; i < 50000 ; i++) {
            sb = new StringBuilder() ;
            String s1 = "a" ;
            String s2 = "b" ;
            String s3 = "c" ;
            String s4 = "d" ;
            String s5 = "e" ;
            sb.append(s1).append(s2).append(s3).append(s4).append(s5) ;
            String s6 = sb.toString() ;

        }
        long l2 = System.currentTimeMillis() ;
        System.out.println(l2-l1);
    }

    public static void test3(){
        long l1 = System.currentTimeMillis() ;
        StringBuffer sb = null ;
        for (int i = 0; i < 50000 ; i++) {
            sb = new StringBuffer() ;
            String s1 = "a" ;
            String s2 = "b" ;
            String s3 = "c" ;
            String s4 = "d" ;
            String s5 = "e" ;
            sb.append(s1).append(s2).append(s3).append(s4).append(s5) ;
            String s6 = sb.toString() ;
        }
        long l2 = System.currentTimeMillis() ;
        System.out.println(l2-l1);
    }
    public static void main(String[] args) {
        StringTest.test3();

    }
}
