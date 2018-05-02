package com.xz.redis.base.utils;

import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

import java.util.List;

/**
 * Created by THink on 2018/3/3.
 */
public class RedisUtils {
    private Jedis jedis = null ;

    @Before
    public void before(){
        jedis = Connection.getConnectionByPool() ;
        System.out.println(jedis.ping());
    }
    @After
    public void after(){
        jedis.close();
    }

    @Test
    public void cleanAll(){
        jedis.flushDB() ;
    }

    @Test
    public void showLog(){
        List<Slowlog> list = jedis.slowlogGet() ;
        for (Slowlog slowlog:list) {
            System.out.println(slowlog.toString());
        }
    }


}
