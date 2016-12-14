package com.xz.rpc.rpc.netty.ser;


import com.xz.rpc.rpc.netty.transion.BasePo;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.*;

/**
 * falcon -- 2016/11/22.
 */
public class IOSer implements BaseSer {
    @Override
    public <T extends BasePo> byte[] serialize(T obj) {
        if (obj==null){
            throw new RuntimeException("序列化对象(" + obj + ")!");
        }
        byte[] bytes = null ;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
            ObjectEncoderOutputStream oos = new ObjectEncoderOutputStream(baos) ;
            oos.writeObject(obj);
            baos.flush();
            oos.flush();

            bytes = baos.toByteArray() ;
            oos.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes ;
    }

    @Override
    public <T extends BasePo> T deserialize(byte[] bytes, Class<T> targetClass) {
        if (bytes==null){
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T t = null ;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes) ;
            ObjectDecoderInputStream ois = new ObjectDecoderInputStream(bais,1024*1024*256) ;
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
