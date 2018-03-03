package com.xz.redis.base.datatype.list;

import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Created by THink on 2018/3/4.
 */
public class RedisList {
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
     * 相当于新增
     * lpush rpush 左侧插入 右侧插入
     * lrange 0 -1 获取全部list
     */
    @Test
    public void push(){
        String key = "list" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.rpush(key,"1","2","3") ;
        pipeline.lpush(key,"4","5","6") ;
        pipeline.lrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }
    /**
     * 相当于删除
     * lpop rpop 左侧插入 右侧插入
     */
    @Test
    public void pop(){
        String key = "list" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.rpush(key,"1","2","3") ;
        pipeline.lpush(key,"4","5","6") ;
        pipeline.lrange(key,0,-1) ;
        pipeline.lpop(key) ;
        pipeline.rpop(key) ;
        pipeline.lrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * linsert : value值的前后位置添加
     * lindex : 检索某一个位置的值
     * llen : 长度
     */
    @Test
    public void insert(){
        String key = "list" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.rpush(key,"1","2","3") ;
        pipeline.lpush(key,"4","5","6") ;
        pipeline.linsert(key, BinaryClient.LIST_POSITION.AFTER,"6","222") ;
        pipeline.linsert(key, BinaryClient.LIST_POSITION.BEFORE,"1","333") ;
        pipeline.linsert(key, BinaryClient.LIST_POSITION.valueOf("BEFORE"),"2","44") ;
        pipeline.lrange(key,0,-1) ;
        pipeline.lindex(key,-1) ;
        pipeline.llen(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * lrem key count value
     * count>0从左往右删除count个 值为value的元素
     * count<0从左往右删除|count|个 值为value的元素
     * count=0全删除
     */
    @Test
    public void remove(){
        String key = "list" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.rpush(key,"1","2","3") ;
        pipeline.lpush(key,"4","5","6") ;
        pipeline.lrange(key,0,-1) ;

        pipeline.lrem(key,1,"1") ;
        pipeline.lrem(key,-1,"2") ;
        pipeline.lrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 截取
     */
    @Test
    public void trim(){
        String key = "list" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.rpush(key,"1","2","3") ;
        pipeline.lpush(key,"4","5","6") ;
        pipeline.lrange(key,0,-1) ;

        pipeline.ltrim(key,2,-1) ;
        pipeline.lrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 设置某一位置的值
     */
    @Test
    public void lset(){
        String key = "list" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.rpush(key,"1","2","3") ;
        pipeline.lpush(key,"4","5","6") ;
        pipeline.lrange(key,0,-1) ;

        pipeline.lset(key,-1,"2") ;
        pipeline.lrange(key,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * brpop 、 blpop 阻塞式
     */
    @Test
    public void block(){
        String key = "list" ;
        jedis.del(key) ;
        jedis.lpush(key,"1") ;
        jedis.rpush(key,"2") ;
        jedis.lpush(key,"3") ;
        jedis.lrange(key,0,-1) ;

        System.out.println("1"+jedis.brpop(0,key));
        System.out.println("2"+jedis.brpop(0,key));
        System.out.println("3"+jedis.blpop(0,key));
        System.out.println("4"+jedis.blpop(0,key));
        jedis.lrange(key,0,-1) ;
    }

    /**
     * 从一个list 迁移到另一个list
     */
    @Test
    public void transfer(){
        String key1 = "list1" ;
        String key2 = "list2" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key1) ;
        pipeline.del(key2) ;
        pipeline.lpush(key1,"1","2","3") ;
        pipeline.rpush(key1,"7","8","9") ;
        pipeline.lpush(key1,"4","5","6") ;
        pipeline.lrange(key1,0,-1) ;
        pipeline.lrange(key2,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
        pipeline.brpoplpush(key1,key2,0);
        pipeline.brpoplpush(key1,key2,0);
        pipeline.brpoplpush(key1,key2,0);
        pipeline.brpoplpush(key1,key2,0);
        pipeline.brpoplpush(key1,key2,0);
        pipeline.brpoplpush(key1,key2,0);
        pipeline.lrange(key1,0,-1) ;
        pipeline.lrange(key2,0,-1) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * ziplist:压缩列表
     * linkedlist:链表
     * quicklist：介于ziplist和linkedlist
     */
    @Test
    public void type(){
        String key = "list1" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key) ;
        pipeline.lpush(key,"1","2","3") ;
        pipeline.objectEncoding(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }
}
