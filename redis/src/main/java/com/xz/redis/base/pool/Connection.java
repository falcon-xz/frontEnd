package com.xz.redis.base.pool;

import com.xz.common.utils.properties.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * Created by Administrator on 2017-7-13.
 */
public class Connection {
    private static Properties config = PropertiesUtil.getProperties("redis.properties") ;
    private static JedisPool jedisPool = null;
    private static int getPort(){
        return Integer.parseInt(config.getProperty("port", "6379")) ;
    }

    private static String getIp(){
        return config.getProperty("ip", "127.0.0.1") ;
    }
    private static int getMaxActive(){
        return Integer.parseInt(config.getProperty("MAX_ACTIVE")) ;
    }
    private static int getMaxIdle(){
        return Integer.parseInt(config.getProperty("MAX_IDLE")) ;
    }
    private static long getMaxWait(){
        return Long.parseLong(config.getProperty("MAX_WAIT")) ;
    }
    private static boolean getTestOnBorrow(){
        return Boolean.parseBoolean(config.getProperty("MAX_WAIT")) ;
    }
    private static String getPassword(){
        return config.getProperty("password", "1qaz!QAZ") ;
    }

    static{
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(getMaxActive());
        config.setMaxIdle(getMaxIdle());
        config.setMaxWaitMillis(getMaxWait());
        config.setTestOnBorrow(getTestOnBorrow());
        jedisPool = new JedisPool(config,getIp() , getPort(),3000,getPassword());
    }

    public static Jedis getConnection(){
        try {
            if(jedisPool!=null){
                return jedisPool.getResource() ;
            }
            return null ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }

    }

}
