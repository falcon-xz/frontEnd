package com.xz.elasticsearch.option.index;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2019/1/8.
 */
public class CreateIndex {
    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        CreateIndexRequest request = new CreateIndexRequest(Parameter.ik_index);

        String source = "{\n" +
                "    \"settings\" : {\n" +
                "        \"number_of_shards\" : 10,\n" +
                "        \"number_of_replicas\" : 2\n" +
                "    },\n" +
                "    \"mappings\" : {\n" +
                "        \""+Parameter.ik_type+"\" : {\n" +
                "            \"properties\" : {\n" +
                "                \"content\" : { \"type\" : \"text\" ,\"analyzer\" : \"ik_max_word\"   },\n" +
                "                \"name\" : { \"type\" : \"text\" ,\"analyzer\" : \"ik_max_word\"   },\n" +
                "                \"createDate\" : { \"type\" : \"date\"  },\n" +
                "                \"price\" : { \"type\" : \"double\"  },\n" +
                "                \"area\" : { \"type\" : \"keyword\"  }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        System.out.println(source);
        request.source(source, XContentType.JSON);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

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
