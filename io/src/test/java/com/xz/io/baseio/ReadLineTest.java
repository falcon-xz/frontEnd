package com.xz.io.baseio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * falcon -- 2017/3/15.
 */
public class ReadLineTest {
    public static int i = 0;

    public static void main(String[] args) {
        File pfile = new File("C:\\Users\\Administrator\\Desktop\\1\\spark_log");
        File[] files1 = pfile.listFiles();
        for (File files:files1) {
            File[] files2 = files.listFiles();
            for (File file : files2) {
                try {
                    FileReader fr = new FileReader(file);
                    LineNumberReader lnr = new LineNumberReader(fr);
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while ((line = lnr.readLine()) != null) {
                        if (line.contains("Exception")){
                            System.out.println(line);
                            System.out.println(file.getName());
                        }
                    }
                    lnr.close();
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
