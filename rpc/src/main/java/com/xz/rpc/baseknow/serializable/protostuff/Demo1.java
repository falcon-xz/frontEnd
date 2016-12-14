package com.xz.rpc.baseknow.serializable.protostuff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * falcon -- 2016/11/19.
 */
public class Demo1 {
    public static void main(String[] args) {
        Transmission transmission = new Transmission("好的",122422,"111fuck") ;

        String filePath = Demo1.class.getClassLoader().getResource("").getPath()+"Protostuff_IO" ;
        File file = new File(filePath) ;
        if (file.exists()){
            file.delete();
        }

        try {
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file) ;
            byte[] bytes = ProtostuffUtil.serialize(transmission) ;
            fos.write(bytes);
            fos.flush();
            fos.close();


            //Student对象反序列化过程
            FileInputStream fis = new FileInputStream(file) ;
            bytes = new byte[(int)file.length()];
            System.out.println(file.length());
            fis.read(bytes);
            fis.close();
            Transmission transmission1 = ProtostuffUtil.deserialize(bytes,Transmission.class) ;
            System.out.println(transmission1.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
    }

    static class Transmission {
        private String name ;
        private int age ;
        private String describe ;

        public Transmission(){

        }

        public Transmission(String name,int age,String describe){
            this.name = name ;
            this.age = age ;
            this.describe = describe ;
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
