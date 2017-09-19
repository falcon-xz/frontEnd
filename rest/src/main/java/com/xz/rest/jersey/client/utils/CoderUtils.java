package com.xz.rest.jersey.client.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017-9-14.
 */
public class CoderUtils {
    /**
     * http 用户名密码验证加密
     * @param str
     * @return
     */
    public static String getBase64(String str){
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public static String getEncode(String str){
        try {
            return URLEncoder.encode(str,"utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public static String getDecode(String str){
        try {
            return URLDecoder.decode(str,"utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null ;
    }

}
