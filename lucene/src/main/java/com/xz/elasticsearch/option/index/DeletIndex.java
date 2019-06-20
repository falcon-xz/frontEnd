package com.xz.elasticsearch.option.index;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2019/1/8.
 */
public class DeletIndex {

    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        DeleteIndexRequest request = new DeleteIndexRequest(Parameter.ik_index);

        try {
            AcknowledgedResponse acknowledgedResponse = client.indices().delete(request, RequestOptions.DEFAULT);

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
