package com.xz.redis.base.datatype.hash;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xz.redis.base.datatype.po.User;
import com.xz.redis.base.pool.Connection;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;

/**
 * Created by THink on 2018/3/3.
 */
public class RedisHash {

    public Jedis jedis ;

    @Before
    public void before(){
        jedis = Connection.getConnection() ;
    }

    @After
    public void after(){
        jedis.disconnect();
    }

    /**
     * hset key field value
     */
    @Test
    public void hset(){
        User user = new User("1","xz","11","男") ;
        String key = "user:"+user.getId() ;
        jedis.hset(key,"name",user.getName()) ;
        jedis.hset(key,"age",user.getAge()) ;
        jedis.hset(key,"sex",user.getSex()) ;

        System.out.println(jedis.hget(key,"name"));
        System.out.println(jedis.hget(key,"name"));
        System.out.println(jedis.hget(key,"name"));
    }

    /**
     * 时间复杂度 0{n}
     * hgetall 返回所有field value
     */
    @Test
    public void hgetall(){
        User user = new User("1","xz","11","男") ;
        String key = "user:"+user.getId() ;
        jedis.hset(key,"name",user.getName()) ;
        jedis.hset(key,"age",user.getAge()) ;
        jedis.hset(key,"sex",user.getSex()) ;

        System.out.println(jedis.hgetAll(key));
    }

    /**
     * hmset key field value [field value]
     */
    @Test
    public void hmset(){
        User user1 = new User("1","xz1","11","男") ;
        User user2 = new User("2","xz2","22","男") ;
        String key1 = "user:"+user1.getId() ;
        String key2 = "user:"+user2.getId() ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.hdel(key1,"name","age","sex") ;
        pipeline.hdel(key2,"name","age","sex") ;
        pipeline.hmset(key1,user1.toMap()) ;
        pipeline.hmset(key2,user2.toMap()) ;
        pipeline.hmget(key1,"name","age","sex");
        pipeline.hmget(key2,"name","age","sex");
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * hash 长度
     */
    @Test
    public void hlen(){
        User user = new User("1","xz","11","女") ;
        String key = "user:"+user.getId() ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.hdel(key,"name","age","sex") ;
        pipeline.hset(key,"name",user.getName()) ;
        pipeline.hset(key,"age",user.getAge()) ;
        pipeline.hset(key,"sex",user.getSex()) ;
        pipeline.hlen(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * hexists key field
     */
    @Test
    public void hexists(){
        User user = new User("1","xz","11","女") ;
        String key = "user:"+user.getId() ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.hdel(key,"name","age","sex") ;
        pipeline.hmset(key,user.toMap()) ;
        pipeline.hexists(key,"name") ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 获取所有field
     * hkeys key field
     */
    @Test
    public void hkeys_hvalues(){
        User user = new User("1","xz","11","女") ;
        String key = "user:"+user.getId() ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.hdel(key,"name","age","sex") ;
        pipeline.hmset(key,user.toMap()) ;
        pipeline.hkeys(key) ;
        pipeline.hvals(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }


    /**
     * ziplist hashtable
     */
    @Test
    public void type (){
        User user = new User("1","xz","11","女") ;
        String key = "user:"+user.getId() ;
        Pipeline pipeline = jedis.pipelined() ;
        pipeline.hdel(key,"name","age","sex") ;
        pipeline.hmset(key,user.toMap()) ;
        pipeline.objectEncoding(key);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 优化 hgetall
     */
    @Test
    public void hscan(){
        String key = "hash:keyhscan" ;
        if (!jedis.exists(key)){
            Map<String,String> map = Maps.newHashMap() ;
            for (int i = 0; i < 1000 ; i++) {
                map.put(i+"",i+"--") ;
            }
            jedis.hmset(key,map) ;
        }
        String cursor = "0" ;
        ScanParams scanParams = new ScanParams() ;
        scanParams.count(10) ;
        scanParams.match("*") ;
        int i = 0 ;
        while (true){
            ScanResult<Map.Entry<String,String>> scanResult = jedis.hscan(key,cursor,scanParams) ;
            List<Map.Entry<String,String>> result = scanResult.getResult() ;
            i = result.size()+i ;
            System.out.print("单次获取数量："+result.size()+",总数量："+i+"---");
            for (Map.Entry<String,String> map:result) {
                System.out.print(map.toString()+"--");
            }
            System.out.println("");
            cursor = scanResult.getStringCursor() ;
            if (cursor.equals("0")){
                break;
            }
        }
        System.out.println("end");
    }
}
