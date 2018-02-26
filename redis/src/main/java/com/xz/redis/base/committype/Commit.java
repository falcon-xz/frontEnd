package com.xz.redis.base.committype;

import com.xz.redis.base.pool.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by THink on 2018/2/26.
 */
public class Commit {
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

    @Test
    public void info(){
        System.out.println(jedis.configGet("maxclients"));
    }

    @Test
    public void normal(){
        jedis.set("hello1", "孙悟空");
        jedis.set("hello2", "猪八戒");
        jedis.set("hello3", "唐僧");
    }

    /**
     * 事务
     *  watch：key
     *  multi：事务连接
     *  队列命令 ...
     *  exec
     *
     */
    @Test
    public void transaction(){
        jedis.set("hello1","hello1") ;
        jedis.set("hello2","hello2") ;
        jedis.set("hello3","hello3") ;
        jedis.watch("hello1","hello2","hello3") ;
        Transaction t = jedis.multi() ;
        t.set("hello1", "孙悟空");
        t.set("hello2", "猪八戒");
        t.set("hello3", "唐僧");
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 执行事务
        List<Object> list =  t.exec();
        for (Object o:list) {
            System.out.println("---"+o);
        }
        jedis.unwatch();
    }

    @Test
    public void pipeline(){
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.set("hello1", "孙悟空");
        pipeline.set("hello2", "猪八戒");
        pipeline.set("hello3", "唐僧");
        List<Object> list = pipeline.syncAndReturnAll() ;
        for (Object o:list) {
            System.out.println(o.toString());
        }
    }
}
