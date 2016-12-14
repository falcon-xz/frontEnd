package com.xz.rpc.baseknow.serializable.base;

import java.io.*;

/**
 * falcon -- 2016/11/21.
 */
public class BaseIOUtil {

    public static <T extends Serializable> byte[] serialize(T t) throws Exception{
        if (t==null){
            throw new Exception("BaseIOUtil serialize 对象为null") ;
        }
        byte[] bytes = null ;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
            ObjectOutputStream oos = new ObjectOutputStream(baos) ;
            oos.writeObject(t);
            oos.flush();
            baos.flush();

            bytes = baos.toByteArray() ;
            oos.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes ;
    }

    public static <T extends Serializable> T deserialize( byte[] bytes)throws Exception{
        if (bytes==null){
            throw new Exception("BaseIOUtil deserialize 对象为null") ;
        }
        T t = null ;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes) ;
            ObjectInputStream ois = new ObjectInputStream(bais) ;
            t = (T)ois.readObject() ;
            ois.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t ;
    }
}
