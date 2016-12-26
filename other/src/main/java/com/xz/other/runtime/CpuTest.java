package com.xz.other.runtime;

/**
 * falcon -- 2016/12/13.
 */
public class CpuTest {
    public static Runtime runtime = Runtime.getRuntime() ;

    public static int getCpuCount(){
        return runtime.availableProcessors() ;
    }

    public static void main(String[] args) {
        System.out.println(CpuTest.getCpuCount());
    }
}
