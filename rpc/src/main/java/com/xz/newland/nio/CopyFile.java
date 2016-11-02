package com.xz.newland.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/11/1.
 */
public class CopyFile {
    public static void main(String[] args) {
        try {
            String inPath = CopyFile.class.getClassLoader().getResource("nio").getPath() ;
            File inFile = new File(inPath+"/5.sws") ;
            FileInputStream fis = new FileInputStream(inFile) ;
            FileChannel inFileChannel = fis.getChannel() ;

            File outFile = new File(inPath+"/5out.sws") ;
            if (outFile.exists()){
                outFile.delete() ;
            }
            outFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outFile) ;
            FileChannel outFileChannel = fos.getChannel() ;
            //outfile 通道传输的长度 及构建信息
            long fileSize = inFileChannel.transferTo(0,inFileChannel.size(),outFileChannel) ;
            int bbSize = 10 ;
            ByteBuffer bb = ByteBuffer.allocate(bbSize) ;
            while (inFileChannel.read(bb)!=-1){
                System.out.println(inFileChannel.read(bb));
                outFileChannel.write(bb) ;
                bb.clear();
            }

            inFileChannel.close();
            outFileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
