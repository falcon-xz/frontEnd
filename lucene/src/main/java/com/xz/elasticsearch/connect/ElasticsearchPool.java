package com.xz.elasticsearch.connect;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Created by xz on 2018/12/29.
 */
public class ElasticsearchPool extends GenericObjectPool<RestHighLevelClient> {
    public ElasticsearchPool(PooledObjectFactory<RestHighLevelClient> factory, GenericObjectPoolConfig<RestHighLevelClient> config) {
        super(factory, config);
    }

}
