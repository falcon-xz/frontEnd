package com.xz.elasticsearch.connect;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Created by xz on 2018/12/28.
 */
public class ElasticsearchConnectionFactory implements PooledObjectFactory<RestHighLevelClient>{
    private ElasticSearchPoolConfig elasticSearchPoolConfig;

    public ElasticsearchConnectionFactory(ElasticSearchPoolConfig elasticSearchPoolConfig) {
        this.elasticSearchPoolConfig = elasticSearchPoolConfig;
    }

    public void setElasticSearchPoolConfig(ElasticSearchPoolConfig elasticSearchPoolConfig) {
        this.elasticSearchPoolConfig = elasticSearchPoolConfig;
    }

    @Override
    public PooledObject<RestHighLevelClient> makeObject() throws Exception {
        RestClientBuilder restClientBuilder = RestClient.builder(elasticSearchPoolConfig.getList().toArray(new HttpHost[elasticSearchPoolConfig.getList().size()])) ;
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder) ;
        return new DefaultPooledObject<>(restHighLevelClient);
    }

    @Override
    public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {

        try {
            return pooledObject.getObject().ping(RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
    }

    @Override
    public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {

    }
}
