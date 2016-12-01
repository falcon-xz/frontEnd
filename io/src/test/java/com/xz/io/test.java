package com.xz.io;

import java.io.File;

/**
 * falcon -- 2016/12/1.
 */
public class test {
    public static void main(String[] args) {
        File file = new File("E:\\test\\test\\test\\1.txt") ;
        file.getParentFile().mkdirs();
    }
}
