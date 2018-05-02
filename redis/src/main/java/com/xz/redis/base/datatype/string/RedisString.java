package com.xz.redis.base.datatype.string;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xz.redis.base.datatype.po.User;
import com.xz.redis.base.pool.Connection;
import com.xz.util.protostuff.ProtostuffUtils;
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
 * Created by THink on 2018/2/24.
 */
public class RedisString {

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

    /**
     * set命令
     * NX|XX:
     *      对应setnx命令:NX:则只有当key不存在是才进行set
     *      对应setxx命令:XX:则只有当kexy已经存在时才进行set
     * EX|PX:
     *      EX:seconds
     *      PX:milliseconds
     * return null 失败 ok 成功
     */
    @Test
    public void setString(){
        String key = "hello" ;
        String s = jedis.set(key,"world1","NX","EX",60*60*24);
        System.out.println("set返回值:"+s);
        System.out.println("key-值:"+jedis.get(key));
    }

    /**
     * set bytes
     */
    @Test
    public void setBytes(){
        User user = new User("1","xz","15","男") ;
        byte[] key = "user".getBytes() ;
        byte[] value = ProtostuffUtils.serialize(user) ;
        jedis.set(key,value) ;

        User user2 = ProtostuffUtils.deserialize(jedis.get(key),User.class) ;
        System.out.println(user2);
    }

    /**
     * mset : mset key1 value1 key2 value2
     */
    @Test
    public void mset(){
        String key1 = "hello1" ;
        String key2 = "hello2" ;
        jedis.mset(key1,key1+key1,key2,key2+key2) ;

        System.out.println(jedis.mget(key1,"1",key2));
    }

    /**
     * 计数： incr decr ; incrby decrby ; incrbyfloat decrbyfloat
     *  return: 1.key 不存在 返回 1
     *          2.key 存在  value为num 返回+1
     *          3.key 存在  value不为num 返回JedisDataException
     */
    @Test
    public void incr(){
        String key = "hello" ;
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key) ;
        pipeline.set(key,"hello") ;
        pipeline.incr(key) ;

        pipeline.del(key) ;
        pipeline.set(key,"1") ;
        pipeline.incr(key) ;

        pipeline.set(key,"1") ;
        pipeline.incr(key) ;
        for (Object o:
        pipeline.syncAndReturnAll()) {
            System.out.println(o.toString()+"--"+o.getClass());
        }
    }

    /**
     * 追加 ：append命令
     */
    @Test
    public void append(){
        String key = "hello" ;
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key) ;
        pipeline.set(key,"hello") ;
        pipeline.append(key,"hello2") ;
        pipeline.get(key);
        System.out.println(pipeline.syncAndReturnAll().toString());
    }

    /**
     *  字符串长度：中文占3 英文占1
     */
    @Test
    public void strlen(){
        String key1 = "hello1" ;
        String key2 = "hello2" ;
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key1,key2) ;
        pipeline.mset(key1,"你好",key2,"nihao") ;
        pipeline.strlen(key1);
        pipeline.strlen(key2);
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     *  设置并返回原值:getset
     */
    @Test
    public void getset(){
        String key = "hello" ;
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key) ;
        pipeline.set(key,"你好") ;
        pipeline.getSet(key,"nihao") ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     *  获得部分字符串:getrange 注意中文 范围前后闭合
     */
    @Test
    public void range(){
        String key = "hello" ;
        Pipeline pipeline = jedis.pipelined();
        pipeline.del(key) ;
        pipeline.set(key,"你是不是好") ;
        pipeline.setrange(key,0,"艹") ;
        pipeline.get(key);
        pipeline.getrange(key,6,11) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     *  内部类型
     */
    @Test
    public void type(){
        String key = "hello" ;
        Pipeline pipeline = jedis.pipelined();
        pipeline.set(key,"hello") ;
        pipeline.objectEncoding(key) ;
        pipeline.set(key,"1") ;
        pipeline.objectEncoding(key) ;
        pipeline.set(key,"one string greater than 39 byte ,,,,,,,,one string greater than 39 byte ,,,,,,,,") ;
        pipeline.objectEncoding(key) ;
        System.out.println(pipeline.syncAndReturnAll());
    }

    /**
     * 优化 key
     */
    @Test
    public void scan(){
        String cursor = "0" ;
        ScanParams scanParams = new ScanParams() ;
        scanParams.count(10) ;
        scanParams.match("*") ;
        int i = 0 ;
        while (true){
            ScanResult<String> scanResult = jedis.scan(cursor,scanParams) ;
            List<String> result = scanResult.getResult() ;
            i = result.size()+i ;
            System.out.println("单次获取数量："+result.size()+",总数量："+i+"---");
            for (String element:result) {
                String type = jedis.type(element) ;
                System.out.println(element+"--"+type);
            }
            cursor = scanResult.getStringCursor() ;
            if (cursor.equals("0")){
                break;
            }
        }
        System.out.println("end");
    }
}
