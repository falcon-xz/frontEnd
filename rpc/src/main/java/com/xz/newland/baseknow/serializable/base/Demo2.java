package com.xz.newland.baseknow.serializable.base;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * falcon -- 2016/11/19.
 * 定义了readObject(ObjectInputStream in)和writeObject(ObjectOutputSteam out)，则采用以下方式进行序列化与反序列化。
 * ObjectOutputStream调用Student对象的writeObject(ObjectOutputStream out)的方法进行序列化。
 * ObjectInputStream会调用Student对象的readObject(ObjectInputStream in)的方法进行反序列化
 * NIO方式
 */
public class Demo2 {
    public static void main(String[] args) {
        Transmission transmission = new Transmission("xzuu",12,"fuck") ;

        String filePath = Demo2.class.getClassLoader().getResource("").getPath()+"demo2" ;
        File file = new File(filePath) ;
        if (file.exists()){
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file) ;
            FileChannel outfc = fos.getChannel() ;
            ByteBuffer bb = ByteBuffer.allocate(1024) ;
            bb.clear() ;
            bb.put(BaseIOUtil.serialize(transmission)) ;
            bb.flip() ;
            outfc.write(bb) ;
            outfc.close();
            fos.close();

            //Student对象反序列化过程
            FileInputStream fis = new FileInputStream(file);
            FileChannel infc = fis.getChannel() ;
            bb.clear();
            infc.read(bb) ;

            Transmission transmission1 = BaseIOUtil.deserialize(bb.array());
            System.out.println("transmission" + transmission1.toString());
            infc.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("删除"+file.delete());

    }
    static class Transmission implements Serializable{
        private String name ;
        private int age ;
        private String describe ;

        public Transmission(String name,int age,String describe){
            this.name = name ;
            this.age = age ;
            this.describe = describe ;
        }
        @Override
        public String toString() {
            return name+"--"+age+"--"+describe;
        }

        private void readObject(ObjectInputStream in) throws IOException{
            System.out.println("反序列化");
            this.name = in.readUTF() ;
            this.age = in.read() ;
            this.describe = in.readUTF() ;
        }

        private void writeObject(ObjectOutputStream out) throws IOException{
            System.out.println("序列化");
            out.writeUTF(this.name);
            out.write(this.age);
            out.writeUTF(this.describe);
        }
    }
}