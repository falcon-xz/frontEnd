package com.xz.io.baseio;

import java.io.*;

/**
 * falcon -- 2016/12/1.
 */
public class LineNumberReaderTest {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1.txt") ;
        File target = new File("E:\\test\\1_LineNumberReaderTest.txt") ;
        try {

            FileReader fr = new FileReader(file) ;
            LineNumberReader lnr = new LineNumberReader(fr) ;
            FileWriter fw = new FileWriter(target) ;
            long l1 = System.currentTimeMillis() ;
            String line = null ;
            while ((line = lnr.readLine())!=null){
                fw.write(line+"\r\n");
            }
            fw.flush();
            long l2 = System.currentTimeMillis() ;
            System.out.println((l2-l1));
            lnr.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
