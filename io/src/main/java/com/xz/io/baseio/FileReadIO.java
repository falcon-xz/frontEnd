package com.xz.io.baseio;

import java.io.*;

/**
 * falcon -- 2016/12/1.
 */
public class FileReadIO {
    public static void main(String[] args) {
        File file = new File("E:\\test\\1.txt") ;
        File target = new File("E:\\test\\1_FileReadIO.txt") ;
        try {
            FileReader fileReader = new FileReader(file) ;
            FileWriter fileWriter = new FileWriter(target) ;
            int i = 0 ;
            char[] chars = new char[9192] ;
            while ((i=fileReader.read(chars))!=-1){
                fileWriter.write(chars);
            }
            fileWriter.flush();
            fileWriter.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
