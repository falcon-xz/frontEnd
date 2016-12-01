package com.xz.io.baseio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * falcon -- 2016/12/1.
 */
public class CreateFile {
    public static void main(String[] args) {
        try {

            long l1 = System.currentTimeMillis() ;
            File file = new File("E:\\test\\1.txt") ;
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile() ;
            }
            FileOutputStream os = new FileOutputStream(file) ;
            for (long i =0 ;i<5000000;i++){
                os.write(("hello+我是第"+i+"人\r\n").getBytes("utf-8"));
                os.flush();
            }
            os.close();
            long l2 = System.currentTimeMillis() ;
            System.out.println("cost:"+(l2-l1)/1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
