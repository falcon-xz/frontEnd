package com.xz.elasticsearch.option.doc;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2019/1/1.
 */
public class InsertES {
    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        IndexRequest indexRequest = new IndexRequest(Parameter.ik_index,Parameter.ik_type,"1") ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(new Date());
        try {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "眼镜");
            jsonMap.put("createDate", c.getTime());
            jsonMap.put("price", "35.7");
            jsonMap.put("area", "东湖区");
            jsonMap.put("content", "蔡司立方非球面近视镜");
            indexRequest.source(jsonMap) ;

            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT) ;
            RestStatus restStatus = indexResponse.status() ;
            System.out.println("rest 状态:"+restStatus.getStatus());
            System.out.println("index id:"+indexResponse.getId());
            System.out.println("index Version:"+indexResponse.getVersion());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndexName());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndex().getName());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndex().getUUID());
            System.out.println("index Result:"+indexResponse.getResult().getLowercase());
            System.out.println("index Result:"+indexResponse.getResult().name());
            System.out.println("index Version:"+indexResponse.getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.add(Calendar.YEAR,-1);
        try {
            indexRequest.id("2") ;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "眼镜");
            jsonMap.put("createDate", c.getTime());
            jsonMap.put("price", "257");
            jsonMap.put("area", "东湖区");
            jsonMap.put("content", "LOHO 防蓝光眼镜男防辐射电脑护目镜女电竞平光镜新品 LHK015 黑色");
            indexRequest.source(jsonMap) ;

            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT) ;
            RestStatus restStatus = indexResponse.status() ;
            System.out.println("rest 状态:"+restStatus.getStatus());
            System.out.println("index id:"+indexResponse.getId());
            System.out.println("index Version:"+indexResponse.getVersion());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndexName());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndex().getName());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndex().getUUID());
            System.out.println("index Result:"+indexResponse.getResult().getLowercase());
            System.out.println("index Result:"+indexResponse.getResult().name());
            System.out.println("index Version:"+indexResponse.getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            indexRequest.id("3") ;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "眼镜");
            jsonMap.put("createDate", c.getTime());
            jsonMap.put("price", "89");
            jsonMap.put("area", "东湖区");
            jsonMap.put("content", "小米（MI）眼镜男女款 TS基础级防蓝光护目镜 米家定制版 黑色镜架");
            indexRequest.source(jsonMap) ;

            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT) ;
            RestStatus restStatus = indexResponse.status() ;
            System.out.println("rest 状态:"+restStatus.getStatus());
            System.out.println("index id:"+indexResponse.getId());
            System.out.println("index Version:"+indexResponse.getVersion());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndexName());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndex().getName());
            System.out.println("index ShardId:"+indexResponse.getShardId().getIndex().getUUID());
            System.out.println("index Result:"+indexResponse.getResult().getLowercase());
            System.out.println("index Result:"+indexResponse.getResult().name());
            System.out.println("index Version:"+indexResponse.getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
