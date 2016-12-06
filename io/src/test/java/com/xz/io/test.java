package com.xz.io;

import com.google.common.base.Charsets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * falcon -- 2016/12/1.
 */
public class test {
    public static void main(String[] args) {
        try {
            CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
            Charset charset = Charset.forName("gbk") ;
            File file = new File("E:\\test\\2.txt") ;
            FileInputStream fis = new FileInputStream(file) ;
            FileChannel fc = fis.getChannel() ;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
            CharBuffer charBuffer = CharBuffer.allocate(1) ;
            int i = 0 ;
            while ((i=fc.read(byteBuffer))!=-1){
                byteBuffer.flip();
                charBuffer.clear();
                decoder.decode(byteBuffer,charBuffer,true);
                System.out.println(charBuffer.position()+"--"+charBuffer.limit());
                charBuffer.flip();
                System.out.println(charBuffer.position()+"--"+charBuffer.limit());
                if(charBuffer.hasRemaining()) {
                    char c = charBuffer.get();
                    System.out.println("char:"+c);
                }
                /*CharBuffer charBuffer2 = charset.newDecoder().decode(byteBuffer);
                charBuffer.flip() ;
                System.out.println(charBuffer2.position()+"--"+charBuffer2.limit());
                char c = charBuffer2.get() ;
                System.out.println(c);
                System.out.println(byteBuffer);*/


                charBuffer.clear();
                byteBuffer.clear() ;
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}
