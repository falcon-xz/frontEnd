package com.xz.elasticsearch.connect;


import com.xz.common.utils.properties.PropertiesUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Properties;

/**
 * Created by xz on 2018/12/27.
 */
public class ElasticsearchConnection {

    private static ElasticsearchPool elasticsearchPool;
    private static RestHighLevelClient restHighLevelClient;

    static {
        String hosts = PropertiesUtil.getProperties("elasticsearch.properties").getProperty("elasticsearch.hosts");
        ElasticSearchPoolConfig elasticSearchPoolConfig = new ElasticSearchPoolConfig(null, hosts);
        elasticsearchPool = new ElasticsearchPool(new ElasticsearchConnectionFactory(elasticSearchPoolConfig), elasticSearchPoolConfig);
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(elasticSearchPoolConfig.getList().toArray(new HttpHost[elasticSearchPoolConfig.getList().size()])));
    }

    public static RestHighLevelClient getResource() {
        try {
            return elasticsearchPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RestHighLevelClient getConnection() {
        return restHighLevelClient;
    }

}
