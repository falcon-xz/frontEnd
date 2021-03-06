package com.xz.rpc.baseknow.serializable.protostuff;

import com.xz.util.protostuff.ProtostuffUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/11/19.
 * protostuff 使用NIO 反序列化
 */
public class Demo2 {

    public static void main(String[] args) {
        Transmission transmission = new Transmission("好的",122422,"111fuck") ;

        String filePath = Demo2.class.getClassLoader().getResource("").getPath() + "Protostuff_NIO";
        File file = new File(filePath) ;
        if (file.exists()){
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file) ;
            FileChannel outfc = fos.getChannel() ;
            ByteBuffer bb = ByteBuffer.allocate(1024) ;
            bb.clear() ;
            bb.put(ProtostuffUtils.serialize(transmission)) ;
            bb.flip() ;
            outfc.write(bb) ;
            fos.flush();
            outfc.close();
            fos.close();

            FileInputStream fis = new FileInputStream(file) ;
            FileChannel infc = fis.getChannel() ;
            bb.clear();
            infc.read(bb) ;
            bb.flip();
            byte[] bytes = new byte[bb.limit()] ;
            bb.get(bytes) ;
            Transmission transmission1 = ProtostuffUtils.deserialize(bytes,Transmission.class) ;
            System.out.println(transmission1.toString());
            fis.close();
        } catch (IOException e) {
        }
        System.out.println("删除"+file.delete());
    }

    static class Transmission {
        private String name;
        private int age;
        private String describe;

        public Transmission() {

        }

        public Transmission(String name, int age, String describe) {
            this.name = name;
            this.age = age;
            this.describe = describe;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer() ;
            sb.append("Transmission{")
                    .append("name").append(name).append(",")
                    .append("age").append(age).append(",")
                    .append("describe").append(describe).append(",")
                    .append("}") ;
            return sb.toString();
        }
    }
}
