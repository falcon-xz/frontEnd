package com.xz.elasticsearch.option.index;

import com.xz.elasticsearch.option.Parameter;
import com.xz.util.http.HttpClientConnect;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2019/1/7.
 */
public class CreateIndexRest {

    @Test
    public void createIndex(){
        String urlStr = "http://localhost:9200/"+ Parameter.ik_index;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"put","direct")
                .bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createMapping(){
        String urlStr = "http://localhost:9200/"+ Parameter.ik_index+"/"+Parameter.ik_type+"/_mapping";
        Map<String,String> headMap = new HashMap<>() ;
        headMap.put("Content-Type","application/json") ;
        String data = "{\n" +
                "        \"properties\": {\n" +
                "            \"content\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"analyzer\": \"ik_max_word\",\n" +
                "                \"search_analyzer\": \"ik_max_word\"\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "}" ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"post","direct")
                .setHeaderMap(headMap).setData(data).setDataType("json").bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void indexDoc(){

        Map<String,String> headMap = new HashMap<>() ;
        headMap.put("Content-Type","application/json") ;
        String[] data = new String[4] ;
        data[0] = "{\"content\":\"美国留给伊拉克的是个烂摊子吗\"}" ;
        data[1] = "{\"content\":\"公安部：各地校车将享最高路权\"}" ;
        data[3] = "{\"content\":\"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船\"}" ;
        data[4] = "{\"content\":\"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首\"}" ;
        for (int i = 0; i < data.length; i++) {
            String urlStr = "http://localhost:9200/"+ Parameter.ik_index+"/"+Parameter.ik_type+"/"+(i+1);
            HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"post","direct")
                    .setHeaderMap(headMap).setData(data[i]).setDataType("json").bulid();
            try {
                System.out.println(connect.sendData() );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void highLightQuery(){
        String urlStr = "http://localhost:9200/"+ Parameter.ik_index+"/"+Parameter.ik_type+"/_search";
        Map<String,String> headMap = new HashMap<>() ;
        headMap.put("Content-Type","application/json") ;
        String data = "{\n" +
                "    \"query\" : { \"match\" : { \"content\" : \"中国\" }},\n" +
                "    \"highlight\" : {\n" +
                "        \"pre_tags\" : [\"<tag1>\", \"<tag2>\"],\n" +
                "        \"post_tags\" : [\"</tag1>\", \"</tag2>\"],\n" +
                "        \"fields\" : {\n" +
                "            \"content\" : {}\n" +
                "        }\n" +
                "    }\n" +
                "}" ;
        HttpClientConnect connect = new HttpClientConnect.HttpClientConnectBuild(urlStr,"post","direct")
                .setHeaderMap(headMap).setData(data).setDataType("json").bulid();
        try {
            System.out.println(connect.sendData() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
