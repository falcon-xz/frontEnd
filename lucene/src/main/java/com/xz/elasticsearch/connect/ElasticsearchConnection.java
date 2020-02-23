package com.xz.elasticsearch.connect;


import com.xz.common.utils.properties.PropertiesUtil;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Properties;

/**
 * Created by xz on 2018/12/27.
 */
public class ElasticsearchConnection {

    private static GenericObjectPool<RestHighLevelClient> elasticsearchPool;
    private static Properties properties = PropertiesUtil.getProperties("elasticsearch.properties");
    private static ElasticSearchPoolConfig elasticSearchPoolConfig ;

    static {
        String hosts = properties.getProperty("elasticsearch.hosts");
        boolean authentication = Boolean.parseBoolean(properties.getProperty("elasticsearch.authentication")) ;
        String username = properties.getProperty("elasticsearch.username");
        String password = properties.getProperty("elasticsearch.password");
        elasticSearchPoolConfig = new ElasticSearchPoolConfig( hosts,authentication,username,password);
    }

    public static RestHighLevelClient getResource() {
        if (elasticsearchPool==null){
            initPool();
        }
        try {
            return elasticsearchPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void initPool(){
        elasticsearchPool = new GenericObjectPool<>(new ElasticsearchConnectionFactory(), elasticSearchPoolConfig);
    }
    public static RestHighLevelClient getConnection() {
        RestClientBuilder restClientBuilder = RestClient.builder(elasticSearchPoolConfig.getHttpHosts()) ;
        if (elasticSearchPoolConfig.isAuthentication()){
            final CredentialsProvider credentialsProvider =
                    new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticSearchPoolConfig.getUsername(),elasticSearchPoolConfig.getPassword()));
            restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(
                        HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider);
                }
            });
        }
        restClientBuilder.setMaxRetryTimeoutMillis(5000);
        return new RestHighLevelClient(restClientBuilder) ;
    }

}
