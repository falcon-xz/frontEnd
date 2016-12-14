package com.xz.io.baseio;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * falcon -- 2016/12/13.
 */
public class TarInputStreamTest {
    public static void main(String[] args) {
        try {
            File file = new File("E:\\test\\hole.tar") ;
            String newFileStr = "E:\\test1\\" ;
            FileInputStream fis = new FileInputStream(file) ;
            TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(fis) ;
            TarArchiveEntry tarArchiveEntry = null ;
            while ((tarArchiveEntry=tarArchiveInputStream.getNextTarEntry())!=null){
                File newFile = new File(newFileStr+tarArchiveEntry.getName()) ;
                if (!newFile.getParentFile().exists()){
                    newFile.getParentFile().mkdirs() ;
                }
                FileOutputStream fos = new FileOutputStream(newFile) ;

                byte[] bytes = new byte[9192] ;
                int i = 0 ;
                while ((i=tarArchiveInputStream.read(bytes))!=-1){
                    fos.write(bytes,0,i);
                }
                fos.flush();
                fos.close();
            }
            tarArchiveInputStream.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
