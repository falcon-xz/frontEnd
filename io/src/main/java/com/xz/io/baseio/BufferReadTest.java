package com.xz.io.baseio;

import java.io.*;

/**
 * falcon -- 2016/12/1.
 */
public class BufferReadTest {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1.txt") ;
        File target = new File("E:\\test\\1_BufferReadIO.txt") ;
        try {
            FileReader fileReader = new FileReader(file) ;
            BufferedReader bufferedReader = new BufferedReader(fileReader) ;
            FileWriter fileWriter = new FileWriter(target) ;
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) ;
            int i = 0 ;
            char[] chars = new char[9192] ;
            long l1 = System.currentTimeMillis() ;
            while ((i=bufferedReader.read(chars))!=-1){
                bufferedWriter.write(chars,0,i);
            }
            long l2 = System.currentTimeMillis() ;
            System.out.println(l2-l1);
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
