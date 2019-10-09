package com.xz.jvm.stack;

import java.nio.ByteBuffer;

/**
 * Created by xz on 2019/8/20.
 */
public class Test {
    public static void main(String[] args) {

        while(1==1){
            ByteBuffer buffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis());
        }

    }
}
