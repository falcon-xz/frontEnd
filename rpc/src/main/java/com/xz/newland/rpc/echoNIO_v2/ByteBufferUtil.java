package com.xz.newland.rpc.echoNIO_v2;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * falcon -- 2016/11/5.
 */
public class ByteBufferUtil {
    public static String toString(ByteBuffer byteBuffer){
        String s = null ;
        byte[] bytes = new byte[byteBuffer.remaining()] ;
        byteBuffer.get(bytes) ;
        try {
            s = new String(bytes,"utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s ;
    }
}
