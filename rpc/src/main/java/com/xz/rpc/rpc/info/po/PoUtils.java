package com.xz.rpc.rpc.info.po;

import java.io.*;

/**
 * falcon -- 2016/12/15.
 */
public class PoUtils {
    public static <T extends BasePoSer> byte[] object2Bytes(T t) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
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

    public static <T extends BasePoSer> T bytes2Object(byte[] bytes) {
        T t = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            t = (T)ois.readObject();
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
        return t;
    }
}
