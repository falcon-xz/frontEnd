package com.xz.elasticsearch.option.analyze;

import com.xz.elasticsearch.option.Parameter;
import com.xz.util.http.HttpClientConnect;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2019/1/6.
 */
public class AnalyzeRestTest {

    @Test
    public void testAnalyze(){
        String urlStr = "http://localhost:9200/_analyze";
        Map<String,String> headMap = new HashMap<>() ;
        headMap.put("Content-Type","application/json") ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"post","direct")
                .setHeaderMap(headMap).setData("{\n" +
                        "\"char_filter\":[],\n" +
                        "\"tokenizer\":\"ik_max_word\",\n" +
                            "\"text\":\"eating an Apple a day \"\n" +
                        "}").setDataType("json").bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void ikIndexAnalyze(){
        String urlStr = "http://localhost:9200/"+ Parameter.ik_index+"/_analyze";
        Map<String,String> headMap = new HashMap<>() ;
        headMap.put("Content-Type","application/json") ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"post","direct")
        .setHeaderMap(headMap).setData("{\"field\":\"content\",\"text\":\"eating an Apple a day 用户名\"}").setDataType("json").bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

