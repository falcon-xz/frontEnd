package com.xz.io.baseio;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 有中文乱码 但是不影响解压
 * falcon -- 2016/12/13.
 */
public class TarOutputStreamTest {
    public static void main(String[] args) {
        try {
            File baseFile = new File("E:\\test\\test") ;
            File[] files = baseFile.listFiles() ;
            FileOutputStream fos = new FileOutputStream("E:\\test\\hole.tar") ;
            TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(fos) ;
            for (File file:files) {
                tarArchiveOutputStream.putArchiveEntry(new TarArchiveEntry(file));
                FileInputStream fis = new FileInputStream(file);
                IOUtils.copy(fis, tarArchiveOutputStream);
                tarArchiveOutputStream.closeArchiveEntry();
                fis.close();
            }
            tarArchiveOutputStream.flush();
            fos.flush();
            tarArchiveOutputStream.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
