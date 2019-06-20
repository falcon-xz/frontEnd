package com.xz.elasticsearch.option.doc;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * Created by xz on 2019/1/2.
 */
public class ExistsES {
    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        GetRequest getRequest = new GetRequest(Parameter.ik_index,Parameter.ik_type,"1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        try {
            boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
            System.out.println(exists);
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
