package com.xz.redis.base.datatype.set;

import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Created by THink on 2018/3/5.
 */
public class RedisSet {

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
     * 添加：sadd 返回成功添加的数量
     * 返回所有元素 : smembers key
     */
    @Test
    public void sadd(){
        String key = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        pipeline.sadd(key,"a","b","c") ;
        pipeline.sadd(key,"b","d") ;
        pipeline.smembers(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 添加：srem 返回成功删除的数量
     * scard 计算元素数量
     */
    @Test
    public void srem(){
        String key = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        pipeline.sadd(key,"a","b","c") ;
        pipeline.sadd(key,"b","d") ;
        pipeline.srem(key,"a","c") ;
        pipeline.scard(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 元素是否存在：sismember
     */
    @Test
    public void sismember(){
        String key = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        pipeline.sadd(key,"a","b","c") ;
        pipeline.sismember(key,"a") ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 随机返回元素(不删除):srandmember
     * 随机返回元素(删除):spop
     */
    @Test
    public void srandmember_spop(){
        String key = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        pipeline.sadd(key,"a","b","c") ;
        pipeline.srandmember(key,2) ;
        pipeline.spop(key,2) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 交集
     */
    @Test
    public void sinter(){
        String key1 = "set:key1" ;
        String key2 = "set:key2" ;
        String key3 = "set:key3" ;
        String finalkey = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key1);
        pipeline.del(key2);
        pipeline.del(key3);
        pipeline.sadd(key1,"fuck","you","ok","no") ;
        pipeline.sadd(key2,"oh","fuck","no") ;
        pipeline.sadd(key3,"oh","ok","fuck") ;
        pipeline.sinterstore(finalkey,key1,key2,key3) ;
        pipeline.smembers(finalkey);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 并集
     */
    @Test
    public void suninon(){
        String key1 = "set:key1" ;
        String key2 = "set:key2" ;
        String key3 = "set:key3" ;
        String finalkey = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key1);
        pipeline.del(key2);
        pipeline.del(key3);
        pipeline.sadd(key1,"fuck","you","ok","no") ;
        pipeline.sadd(key2,"oh","fuck","no") ;
        pipeline.sadd(key3,"oh","ok","fuck") ;
        pipeline.sunionstore(finalkey,key1,key2,key3) ;
        pipeline.smembers(finalkey);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 差集 以一个set为基准 求差集
     */
    @Test
    public void sdiff(){
        String key1 = "set:key1" ;
        String key2 = "set:key2" ;
        String key3 = "set:key3" ;
        String finalkey1 = "set:finalkey1" ;
        String finalkey2 = "set:finalkey2" ;
        String finalkey3 = "set:finalkey3" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key1);
        pipeline.del(key2);
        pipeline.del(key3);
        pipeline.del(finalkey1);
        pipeline.del(finalkey2);
        pipeline.del(finalkey3);
        pipeline.sadd(key1,"fuck","you1","ok","no") ;
        pipeline.sadd(key2,"oh","fuck","no") ;
        pipeline.sadd(key3,"oh","ok","fuck","you3") ;
        pipeline.sdiffstore(finalkey1,key1,key2,key3) ;
        pipeline.sdiffstore(finalkey2,key2,key1,key3) ;
        pipeline.sdiffstore(finalkey3,key3,key1,key2) ;
        pipeline.smembers(finalkey1);
        pipeline.smembers(finalkey2);
        pipeline.smembers(finalkey3);
        System.out.println(pipeline.syncAndReturnAll());
    }


    /**
     *
     */
    @Test
    public void type(){
        String key = "set:key" ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.del(key);
        pipeline.sadd(key,"a","b","c") ;
        pipeline.objectEncoding(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }


    /**
     * 优化smembers
     * TODO
     */
    @Test
    public void sscan(){
        String key = "set:key" ;
        jedis.sscan(key,"") ;
    }
}
