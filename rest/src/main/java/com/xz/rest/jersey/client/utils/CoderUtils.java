package com.xz.rest.jersey.client.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017-9-14.
 */
public class CoderUtils {
    private final static String charSet = "utf-8" ;
    /**
     * http 用户名密码验证加密
     * @param str
     * @return
     */
    public static String getEncodeBase64(String str){
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes(charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = "Basic "+new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * http 用户名密码验证加密
     * @param str
     * @return
     */
    public static String getDecodeBase64(String str) throws IOException{
        String token = null ;
        try {
            String secret = str.substring(6) ;
            byte[] decoded =  new BASE64Decoder().decodeBuffer(secret) ;
            token = new String(decoded, charSet);
        } catch (UnsupportedEncodingException e) {
            throw e ;
        }
        return token;
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

    public static void main(String[] args) {
        String secret = CoderUtils.getEncodeBase64("ADMIN:KYLIN") ;
        try {
            String ret = CoderUtils.getDecodeBase64(secret) ;
            System.out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
