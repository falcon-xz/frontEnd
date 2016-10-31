package com.xz.newland.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/10/31.
 */
public class MyChannel {
    public static void main(String[] args) {
        try {
            String path  = MyChannel.class.getClassLoader().getResource("nio/5.sws").getPath() ;
            File file = new File(path) ;
            FileInputStream fis = new FileInputStream(file) ;
            FileChannel FileChannel = fis.getChannel() ;
            ByteBuffer bb = ByteBuffer.allocate(45) ;
            int i = FileChannel.read(bb) ;
            bb.flip() ;
            bb.position(5) ;
            bb.flip() ;

            System.out.println("capacity--"+bb.capacity());
            System.out.println("position--"+bb.position());
            System.out.println("limit--"+bb.limit());
            System.out.println(bb.hasRemaining());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
