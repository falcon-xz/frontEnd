package com.xz.newland.rpc.echoNIO_v3;

import java.io.*;

/**
 * Created by xz on 2016/10/26.
 */
class Config {
    final static String IP = "127.0.0.1";
    final static int PORT = 9999;

    static byte[] object2Bytes(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            baos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                    oos = null;
                }
                if (baos != null) {
                    baos.close();
                    baos = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    static Object bytes2Object(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                    ois = null;
                }
                if (bais != null) {
                    bais.close();
                    bais = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
