package com.xz.io.basenio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/12/1.
 */
public class CreateFileAlways {
    public static void main(String[] args) {
        try {
            File file = new File("E:\\test\\2.txt") ;
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile() ;
            }
            FileOutputStream os = new FileOutputStream(file) ;
            FileChannel fc = os.getChannel() ;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;

            for (long i =0 ;i<5000000;i++){
                Thread.sleep(100);
                byteBuffer.clear();
                byteBuffer.put(("hello+我是第"+i+"人\r\n").getBytes("utf-8")) ;
                byteBuffer.flip();
                fc.write(byteBuffer);
            }
            fc.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
