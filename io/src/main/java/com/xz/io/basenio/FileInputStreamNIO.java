package com.xz.io.basenio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/12/1.
 */
public class FileInputStreamNIO {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1.txt") ;
        File target = new File("E:\\test\\1_FileInputStreamNIO.txt") ;
        try {

            FileInputStream fis = new FileInputStream(file) ;
            FileChannel fileChannel = fis.getChannel() ;
            FileOutputStream fos = new FileOutputStream(target) ;
            FileChannel fileChannel2 = fos.getChannel() ;

            ByteBuffer byteBuffer = ByteBuffer.allocate(9192*2) ;
            long l1 = System.currentTimeMillis() ;
            while(fileChannel.read(byteBuffer)!=-1){
                byteBuffer.flip() ;
                fileChannel2.write(byteBuffer) ;
                byteBuffer.clear();
            }
            fileChannel.close();
            long l2 = System.currentTimeMillis() ;
            fis.close();
            System.out.println((l2-l1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
