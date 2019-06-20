package com.xz.elasticsearch.option.doc;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Created by xz on 2019/1/1.
 */
public class RemoveES {


    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        DeleteRequest deleteRequest = new DeleteRequest(Parameter.ik_index,Parameter.ik_type,"1") ;
        try {
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(deleteResponse.status().getStatus());
            System.out.println(deleteResponse.getId());
            System.out.println(deleteResponse.getSeqNo());
            System.out.println(deleteResponse.getVersion());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            deleteRequest.id("2");
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(deleteResponse.status().getStatus());
            System.out.println(deleteResponse.getId());
            System.out.println(deleteResponse.getSeqNo());
            System.out.println(deleteResponse.getVersion());

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
