package com.xz.util.http;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2018/12/24.
 */
public class PoolHttpTest {


    @Test
    public void testGet1(){
        String urlStr = "https://movie.douban.com/review/best/?start=0" ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get","direct").bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet2(){
        String urlStr = "http://www.baidu.com/s?rsv_spt=1&rsv_iqid=0xd91d3ae60000c587&issp=1&f=3&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=98012088_5_dg&ch=12&rsv_enter=0&rsv_t=6872BPKNdPHJQV1gWR1AjanHxPOJ%2Bqk%2BeEfQw7L1TrbKPGcBLKD89nuO4NM1sNsY9V2bFg&oq=httpget%2520%25E8%25AE%25BE%25E7%25BD%25AE%25E8%25AF%25B7%25E6%25B1%2582%25E5%258F%2582%25E6%2595%25B0&rsv_pq=a8652c8d0001bff6&inputT=350&rsv_sug3=19&rsv_sug1=11&rsv_sug7=100&rsv_sug2=0&rsv_sug4=2353";
        Map<String,String> map = new HashMap<>() ;
        map.put("wd","http") ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get","direct").setParamsMap(map).
                bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost1(){
        String urlStr = "http://localhost:8080/f/v1/course";
        Map<String,String> map = new HashMap<>() ;
        map.put("token","") ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"post","direct").
                setParamsMap(map).setDataType("form").
                bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostGrafana(){
        String plainCredentials = "admin:admin";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        System.out.println();
        String urlStr = "http://localhost:3000/api/admin/settings";
        Map<String,String> headMap = new HashMap<>() ;
        headMap.put("Authorization","Basic "+base64Credentials) ;
        headMap.put("Accept","application/json") ;
        headMap.put("Content-Type","application/json") ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get","direct").
                setHeaderMap(headMap).
                bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPic1(){
        String urlStr = "https://avatar.csdn.net/6/0/C/3_u011288271.jpg";
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get","direct").
                bulid();
        connect.httpGetResource() ;
    }

    @Test
    public void testGetPic2(){
        String urlStr = "https://cdn2.jianshu.io/assets/web/nav-logo-4c7bbafe27adc892f3046e6978459bac.png";
        try {
            FileUtils.copyURLToFile(new URL(urlStr),new File("D://xxx.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPool(){
        String type = "pool" ;
        //String type = "direct" ;
        String urlStr = null ;
        HttpClientConnect connect = null;
        for ( int i = 0; i < 3; i++) {
            final int ii = i*20 ;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String urlStr = "https://movie.douban.com/review/best/?start="+ii ;
                    HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get",type).bulid();
                    try {
                        System.out.println(ii+"--"+connect.sendData().substring(0,20)) ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("---"+ii);
        }

        System.out.println(1);

        urlStr = "https://avatar.csdn.net/6/0/C/3_u011288271.jpg";
         connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get",type).
                bulid();
        connect.httpGetResource() ;

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(2);
        urlStr = "https://movie.douban.com/review/best/?start=40" ;
        connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"get",type).bulid();
        try {
            connect.sendData() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(23);
    }

}
