package com.xz.io.baseio;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * falcon -- 2016/12/13.
 */
public class GzipInputStreamTest {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1_GzipOutputStreamTest.txt") ;
        File target = new File("E:\\test\\1_GipInputStreamTest.gz") ;

        try {
            FileInputStream fis = new FileInputStream(target) ;
            GZIPInputStream gzipInputStream = new GZIPInputStream(fis) ;
            FileOutputStream fos = new FileOutputStream(file) ;
            int i = 0 ;
            byte[] bytes = new byte[9192] ;
            while ((i= gzipInputStream.read(bytes))!=-1){
                fos.write(bytes,0,i);
            }
            fos.flush();
            fos.close();
            gzipInputStream.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
