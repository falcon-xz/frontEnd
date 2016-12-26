package com.xz.rpc.baseknow.serializable.base;

import java.io.*;

/**
 * falcon -- 2016/11/18.
 * IO 方式
 */
public class Demo1 {
    public static void main(String[] args) {
        Transmission transmission = new Transmission("xz",12) ;

        String filePath = Demo1.class.getClassLoader().getResource("").getPath()+"demo2" ;
        File file = new File(filePath) ;
        if (file.exists()){
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file) ;
            ObjectOutputStream oos = new ObjectOutputStream(fos) ;
            oos.writeObject(transmission);
            oos.flush();

            oos.close();
            fos.close();

            //Student对象反序列化过程
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Transmission transmission1 = (Transmission) ois.readObject();
            System.out.println("transmission" + transmission1.toString());
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        file.delete();
        System.out.println();

    }
    static class Transmission implements Serializable{
        private String name ;
        private int age ;

        public Transmission(String name,int age){
            this.name = name ;
            this.age = age ;
        }
        @Override
        public String toString() {
            return name+"--"+age;
        }
    }
}
