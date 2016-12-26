package com.xz.other.utils;

import java.util.Arrays;

/**
 * falcon -- 2016/12/22.
 */
public class ArrayCopy {
    public static void main(String[] args) {
        String[] strings = new String[]{"1","2","3"} ;
        String[] strings2 = Arrays.copyOfRange(strings,1,3) ;
        System.out.println(Arrays.asList(strings2));
    }
}
