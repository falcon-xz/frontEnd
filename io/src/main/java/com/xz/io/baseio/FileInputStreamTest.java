package com.xz.io.baseio;

import java.io.*;

/**
 * falcon -- 2016/12/1.
 */
public class FileInputStreamTest {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1.txt") ;
        File target = new File("E:\\test\\1_FileInputStreamIO.txt") ;
        try {

            FileInputStream fis = new FileInputStream(file) ;
            FileOutputStream fos = new FileOutputStream(target) ;
            int size = 9192 ;
            byte[] bytes = new byte[size] ;
            int i = 0 ;
            long l1 = System.currentTimeMillis() ;
            while ((i=fis.read(bytes))!=-1){
                fos.write(bytes,0,i);
            }
            fos.flush();
            long l2 = System.currentTimeMillis() ;
            fos.close();
            fis.close();

            System.out.println((l2-l1));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
