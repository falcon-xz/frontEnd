package com.xz.elasticsearch.option.analyze;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.DetailAnalyzeResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2019/1/7.
 */
public class AnalyzeTest {
    public static void main(String[] args) {
        RestHighLevelClient client = ElasticsearchConnection.getConnection() ;
        AnalyzeRequest request = new AnalyzeRequest();

        request.text("<br>eating an Apple a day</br>") ;
        request.analyzer("ik_max_word");
        //request.analyzer("english");
        //request.tokenizer("standard");
        request.addCharFilter("html_strip");
        request.addTokenFilter("lowercase");
        //request.explain(true);
        //request.attributes("keyword", "content");
        try {
            AnalyzeResponse response = client.indices().analyze(request, RequestOptions.DEFAULT);
            List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
            for (AnalyzeResponse.AnalyzeToken analyzeToken:tokens) {
                System.out.println(analyzeToken.getTerm()+":"+analyzeToken.getPosition());
            }

            DetailAnalyzeResponse detail = response.detail();
            System.out.println(detail);
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
