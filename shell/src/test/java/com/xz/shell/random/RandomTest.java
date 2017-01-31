package com.xz.shell.random;

import java.util.Random;

/**
 * falcon -- 2017/1/31.
 */
public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random() ;
        System.out.println(random.nextInt(2));
    }
}
