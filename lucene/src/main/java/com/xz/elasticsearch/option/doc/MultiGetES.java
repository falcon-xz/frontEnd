package com.xz.elasticsearch.option.doc;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by xz on 2019/1/2.
 */
public class MultiGetES {

    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true,new String[] {"name", "_score"}, Strings.EMPTY_ARRAY) ;
        multiGetRequest.add(new MultiGetRequest.Item(Parameter.ik_index,Parameter.ik_type,"1")
                .fetchSourceContext(fetchSourceContext));
        multiGetRequest.add(new MultiGetRequest.Item(Parameter.ik_index,Parameter.ik_type,"2")
                .fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));

        try {
            MultiGetResponse response = client.mget(multiGetRequest, RequestOptions.DEFAULT);

            Iterator<MultiGetItemResponse> it = response.iterator() ;
            while (it.hasNext()){
                MultiGetItemResponse itemResponse = it.next() ;
                String index = itemResponse.getIndex();
                String type = itemResponse.getType();
                String id = itemResponse.getId();
                System.out.println("index:" + index + "; type:" + type + "; id:" + id);
                if(itemResponse.getFailure() != null) {
                    Exception e = itemResponse.getFailure().getFailure();
                    ElasticsearchException ee = (ElasticsearchException) e;
                    System.out.println(ee.getMessage());
                }

                GetResponse getResponse = itemResponse.getResponse();

                if (getResponse !=null && getResponse.isExists()) {
                    long version = getResponse.getVersion();
                    String sourceAsString = getResponse.getSourceAsString();
                    System.out.println("查询的版本为："+version);
                    System.out.println(sourceAsString);
                    Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                    if (sourceAsMap!=null){
                        for (Map.Entry<String,Object> entry:sourceAsMap.entrySet()) {
                            System.out.println(entry.getKey()+"--"+entry.getValue());
                        }
                    }
                } else {
                    System.out.println("没有查询到相应文档");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
