package com.xz.redis.base.datatype.sortset;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by THink on 2018/3/11.
 */
public class RedisSortSet {

    private Jedis jedis = null;

    @Before
    public void before() {
        jedis = Connection.getConnection();
        System.out.println(jedis.ping());
    }

    @After
    public void after() {
        jedis.close();
    }

    /**
     * zadd 添加成员
     * zrevrangeWithScores 返回byte[]的value 和 string 分值
     * 4个参数针对成员
     * nx:member必须不存在,才可以设置成功,用于新增
     * xx:member必须存在,才可以设置成功,用于更新
     * ch:返回此次操作后,有序集合元素和分数发生变化的个数
     * incr:对分值增加
     */
    @Test
    public void zadd() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        ZAddParams zAddParams = ZAddParams.zAddParams();
        zAddParams.ch();
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        map.clear();
        map.put("xz4", 3d);
        map.put("xz3", 4d);
        pipeline.zadd(key, map, zAddParams);
        pipeline.zrange(key, 0, -1);
        pipeline.zrevrangeWithScores(key, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zcard 成员个数
     */
    @Test
    public void zcard() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zrange(key, 0, -1);
        pipeline.zcard(key);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zscore 成员分值
     */
    @Test
    public void zscore() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zrange(key, 0, -1);
        pipeline.zscore(key, "xz1");
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zrank 成员的排名
     */
    @Test
    public void zrank() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zrank(key, "xz2");
        pipeline.zrevrank(key, "xz2");
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zrank 删除成员
     */
    @Test
    public void zrem() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zrem(key, "xz1", "xz2");
        pipeline.zrange(key, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zincrby 增加成员分值
     */
    @Test
    public void zincrby() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zincrby(key, 4d, "xz1");
        pipeline.zrange(key, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zrangeByScoreWithScores 返回指定分数范围的成员 byte[]
     */
    @Test
    public void zrangeByScoreWithScores() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("1", 4d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zrangeByScoreWithScores(key, 2d, 4d);
        pipeline.zrangeByScoreWithScores(key, 2d, 4d, 1, 2);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zcount 返回指定分值的成员个数
     */
    @Test
    public void zcount() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("xz1", 4d);
        map.put("xz2", 6d);
        map.put("1", 4d);
        map.put("xz3", 2d);
        pipeline.zadd(key, map);
        pipeline.zcount(key, 2d, 4d);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * zremrangeByRank 删除指定排名内的升序元素
     * zremrangeByScore 删除指定分值内的升序元素
     * zremrangeByLex 前置条件：所有成员的分值都相同 删除指定升序元素的范围 元素前要加[
     */
    @Test
    public void zremrange() {
        String key = "zset:key";
        Pipeline pipeline = jedis.pipelined();
        // zremrangeByRank
        pipeline.del(key);
        Map<String, Double> map = new HashMap<>();
        map.put("1", 1d);
        map.put("2", 2d);
        map.put("3", 3d);
        map.put("4", 4d);
        pipeline.zadd(key, map);
        pipeline.zremrangeByRank(key, 1, 2);
        pipeline.zrange(key, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());

        // zremrangeByScore
        pipeline.del(key);
        map.clear();
        map.put("1", 1d);
        map.put("2", 2d);
        map.put("3", 3d);
        map.put("4", 4d);
        pipeline.zadd(key, map);
        pipeline.zremrangeByScore(key, 2, 3);
        pipeline.zrange(key, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());

        // zremrangeByLex
        pipeline.del(key);
        map.clear();
        map.put("1", 1d);
        map.put("2", 1d);
        map.put("3", 1d);
        map.put("4", 1d);
        pipeline.zadd(key, map);
        pipeline.zremrangeByLex(key, "[2", "[3");
        pipeline.zrange(key, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 交集
     * 对于相同元素取交集  并对分值进行sum max min操作
     * 权重 相对于集合
     */
    @Test
    public void zinterstore() {
        String key1 = "zset:key1";
        String key2 = "zset:key2";
        String keyFinal1 = "zset:keyFinal1";
        String keyFinal2 = "zset:keyFinal2";
        String keyFinal3 = "zset:keyFinal3";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key1);
        pipeline.del(key2);
        pipeline.del(keyFinal1);
        pipeline.del(keyFinal2);
        pipeline.del(keyFinal3);
        Map<String, Double> map = new HashMap<>();
        map.put("1", 1d);
        map.put("2", 2d);
        map.put("3", 3d);
        map.put("4", 4d);
        pipeline.zadd(key1, map);
        map.clear();
        map.put("3", 5d);
        map.put("4", 7d);
        map.put("5", 5d);
        map.put("6", 6d);
        pipeline.zadd(key2, map);
        ZParams zParams1 = new ZParams();
        zParams1.aggregate(ZParams.Aggregate.SUM);
        zParams1.weightsByDouble(0.5, 1);
        pipeline.zinterstore(keyFinal1, zParams1, key1, key2);

        ZParams zParams2 = new ZParams();
        zParams2.aggregate(ZParams.Aggregate.MAX);
        pipeline.zinterstore(keyFinal2, zParams2, key1, key2);

        ZParams zParams3 = new ZParams();
        zParams3.aggregate(ZParams.Aggregate.MIN);
        pipeline.zinterstore(keyFinal3, zParams3, key1, key2);
        pipeline.zrangeWithScores(keyFinal1, 0, -1);
        pipeline.zrangeWithScores(keyFinal2, 0, -1);
        pipeline.zrangeWithScores(keyFinal3, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());

    }

    /**
     * 并集
     * 对于相同元素取交集  并对分值进行sum max min操作
     * 权重 相对于集合
     */
    @Test
    public void zunionstore() {
        String key1 = "zset:key1";
        String key2 = "zset:key2";
        String keyFinal1 = "zset:keyFinal1";
        String keyFinal2 = "zset:keyFinal2";
        String keyFinal3 = "zset:keyFinal3";
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key1);
        pipeline.del(key2);
        pipeline.del(keyFinal1);
        pipeline.del(keyFinal2);
        pipeline.del(keyFinal3);
        Map<String, Double> map = new HashMap<>();
        map.put("1", 1d);
        map.put("2", 2d);
        map.put("3", 3d);
        map.put("4", 4d);
        pipeline.zadd(key1, map);
        map.clear();
        map.put("3", 5d);
        map.put("4", 7d);
        map.put("5", 5d);
        map.put("6", 6d);
        pipeline.zadd(key2, map);
        ZParams zParams1 = new ZParams();
        zParams1.aggregate(ZParams.Aggregate.SUM);
        zParams1.weightsByDouble(0.5, 1);
        pipeline.zunionstore(keyFinal1, zParams1, key1, key2);

        ZParams zParams2 = new ZParams();
        zParams2.aggregate(ZParams.Aggregate.MAX);
        pipeline.zunionstore(keyFinal2, zParams2, key1, key2);

        ZParams zParams3 = new ZParams();
        zParams3.aggregate(ZParams.Aggregate.MIN);
        pipeline.zunionstore(keyFinal3, zParams3, key1, key2);
        pipeline.zrangeWithScores(keyFinal1, 0, -1);
        pipeline.zrangeWithScores(keyFinal2, 0, -1);
        pipeline.zrangeWithScores(keyFinal3, 0, -1);
        System.out.println(pipeline.syncAndReturnAll());
    }


    @Test
    public void zscan() {
        String key = "zset:keyzscan";
        if (!jedis.exists(key)) {
            Map<String,Double> map = Maps.newHashMap();
            for (int i = 0; i < 1000; i++) {
                map.put(i+"",Double.parseDouble(i+"")) ;
            }
            jedis.zadd(key,map);
        }
        String cursor = "0";
        ScanParams scanParams = new ScanParams();
        scanParams.count(10);
        scanParams.match("*");
        int i = 0;
        while (true) {
            ScanResult<Tuple> scanResult = jedis.zscan(key, cursor, scanParams);
            List<Tuple> result = scanResult.getResult();
            i = result.size() + i;
            System.out.print("单次获取数量：" + result.size() + ",总数量：" + i + "---");
            for (Tuple tuple : result) {
                System.out.print(tuple.getElement()+"="+tuple.getScore()+"--");
            }
            System.out.println("");
            cursor = scanResult.getStringCursor();
            if (cursor.equals("0")) {
                break;
            }
        }
        System.out.println("end");
    }
}
