package com.xz.redis.base.datatype.sortset;

import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by THink on 2018/3/11.
 */
public class RedisSortSet {

    private Jedis jedis = null ;

    @Before
    public void before(){
        jedis = Connection.getConnection() ;
        System.out.println(jedis.ping());
    }
    @After
    public void after(){
        jedis.close();
    }

    /**
     * zadd 添加成员
     */
    @Test
    public void zadd(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",251d) ;
        map.put("xz2",351d) ;
        map.put("xz3",151d) ;
        pipeline.zadd(key,map) ;
        pipeline.zrange(key,0,-1) ;
        pipeline.zrevrangeWithScores(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }
}
