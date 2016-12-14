package com.xz.io.baseio;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * falcon -- 2016/12/13.
 */
public class GzipOutputStreamTest {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1.txt") ;
        File target = new File("E:\\test\\1_GipInputStreamTest.gz") ;

        try {
            FileInputStream fis = new FileInputStream(file) ;
            FileOutputStream fos = new FileOutputStream(target) ;
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fos) ;
            int i = 0 ;
            byte[] bytes = new byte[9192] ;

            while ((i= fis.read(bytes))!=-1){
                gzipOutputStream.write(bytes,0,i);
            }
            gzipOutputStream.flush();
            fos.flush();
            gzipOutputStream.close();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
