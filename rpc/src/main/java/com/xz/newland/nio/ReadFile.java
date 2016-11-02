package com.xz.newland.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/10/31.
 */
public class ReadFile {
    public static void main(String[] args) {
        try {
            int size = 10 ;
            String path  = ReadFile.class.getClassLoader().getResource("nio/5.sws").getPath() ;
            File file = new File(path) ;
            FileInputStream fis = new FileInputStream(file) ;
            FileChannel fileChannel = fis.getChannel() ;
            //设置 ByteBuffer 为20个字节大小
            ByteBuffer bb = ByteBuffer.allocate(size) ;
            //设置字节数组对象 接收ByteBuffer读取的数据
            byte[] bytes = new byte[size] ;
            StringBuffer sb = new StringBuffer() ;
            while(fileChannel.read(bb)!=-1){
                //读转写
                bb.flip() ;
                //剩下的字节数量 ： 通过通道获得的字节数 最后一次通道字节数会不够
                int remain = bb.remaining() ;
                if (remain!=size){
                    bytes = new byte[remain] ;
                }
                bb.get(bytes) ;
                sb.append(new String(bytes,"utf-8")) ;
                bb.clear() ;
            }
            System.out.println(sb.toString());
            fileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
