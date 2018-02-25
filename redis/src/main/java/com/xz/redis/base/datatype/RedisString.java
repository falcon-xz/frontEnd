package com.xz.redis.base.datatype;

import com.xz.redis.base.pool.Connection;
import com.xz.util.protostuff.ProtostuffUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by THink on 2018/2/24.
 */
public class RedisString {

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
     * 字符串
     */
    @Test
    public void setString(){

        /**
         * NX|XX:
         *      NX:则只有当key不存在是才进行set
         *      XX:则只有当key已经存在时才进行set
         * EX|PX:
         *      EX:seconds
         *      PX:milliseconds
         * return null 失败 ok 成功
         */
        String s = jedis.set("hello","world1","NX","EX",60*60*24);
        System.out.println("set返回值:"+s);
        String ss = jedis.get("hello") ;
        System.out.println("key-值:"+ss);
    }

    /**
     * bytes
     */
    @Test
    public void setBytes(){
        User user = new User("xz","15","男") ;
        byte[] key = "user".getBytes() ;
        byte[] value = ProtostuffUtils.serialize(user) ;
        jedis.set(key,value) ;

        User user2 = ProtostuffUtils.deserialize(jedis.get(key),User.class) ;
        System.out.println(user2);
    }


}
