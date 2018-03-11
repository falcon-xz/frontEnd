package com.xz.redis.base.datatype.sortset;

import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.params.sortedset.ZAddParams;

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
     * zrevrangeWithScores 返回byte[]的value 和 string 分值
     * 4个参数针对成员
     *      nx:member必须不存在,才可以设置成功,用于新增
     *      xx:member必须存在,才可以设置成功,用于更新
     *      ch:返回此次操作后,有序集合元素和分数发生变化的个数
     *      incr:对分值增加
     */
    @Test
    public void zadd(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        ZAddParams zAddParams = ZAddParams.zAddParams() ;
        zAddParams.ch();
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        map.clear();
        map.put("xz4",3d) ;
        map.put("xz3",4d) ;
        pipeline.zadd(key,map,zAddParams) ;
        pipeline.zrange(key,0,-1) ;
        pipeline.zrevrangeWithScores(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zcard 成员个数
     */
    @Test
    public void zcard(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zrange(key,0,-1) ;
        pipeline.zcard(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zscore 成员分值
     */
    @Test
    public void zscore(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zrange(key,0,-1) ;
        pipeline.zscore(key,"xz1") ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zrank 成员的排名
     */
    @Test
    public void zrank(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zrank(key,"xz2") ;
        pipeline.zrevrank(key,"xz2") ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zrank 删除成员
     */
    @Test
    public void zrem(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zrem(key,"xz1","xz2");
        pipeline.zrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zincrby 增加成员分值
     */
    @Test
    public void zincrby(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zincrby(key,4d,"xz1");
        pipeline.zrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zrangeByScoreWithScores 返回指定分数范围的成员 byte[]
     */
    @Test
    public void zrangeByScoreWithScores(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("1",4d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zrangeByScoreWithScores(key,2d,4d);
        pipeline.zrangeByScoreWithScores(key,2d,4d,1,2);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zcount 返回指定分值的成员个数
     */
    @Test
    public void zcount(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("xz1",4d) ;
        map.put("xz2",6d) ;
        map.put("1",4d) ;
        map.put("xz3",2d) ;
        pipeline.zadd(key,map) ;
        pipeline.zcount(key,2d,4d);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zremrangeByRank 删除指定排名内的升序元素
     * zremrangeByScore 删除指定分值内的升序元素
     * zremrangeByLex 前置条件：所有成员的分值都相同 删除指定升序元素的范围 元素前要加[
     */
    @Test
    public void zremrange(){
        String key = "zset:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        // zremrangeByRank
        pipeline.del(key);
        Map<String,Double> map = new HashMap<>() ;
        map.put("1",1d) ;
        map.put("2",2d) ;
        map.put("3",3d) ;
        map.put("4",4d) ;
        pipeline.zadd(key,map) ;
        pipeline.zremrangeByRank(key,1,2);
        pipeline.zrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());

        // zremrangeByScore
        pipeline.del(key);
        map.clear();
        map.put("1",1d) ;
        map.put("2",2d) ;
        map.put("3",3d) ;
        map.put("4",4d) ;
        pipeline.zadd(key,map) ;
        pipeline.zremrangeByScore(key,2,3);
        pipeline.zrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());

        // zremrangeByLex
        pipeline.del(key);
        map.clear();
        map.put("1",1d) ;
        map.put("2",1d) ;
        map.put("3",1d) ;
        map.put("4",1d) ;
        pipeline.zadd(key,map) ;
        pipeline.zremrangeByLex(key,"[2","[3");
        pipeline.zrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

}
