package com.xz.redis.demo1;

import com.xz.redis.pool.Connection;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2017-7-12.
 */
public class Main {
    public static void main(String[] args) {
        Jedis jedis = Connection.getConnection();
        String echo = jedis.echo("name");
        String name = jedis.get("name");
        System.out.println(echo+"--"+name);
    }
}
