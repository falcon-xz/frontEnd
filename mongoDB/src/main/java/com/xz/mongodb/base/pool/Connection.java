package com.xz.mongodb.base.pool;

import com.xz.common.utils.properties.PropertiesUtil;

import java.util.Properties;

/**
 * Created by xz on 2018/10/7.
 */
public class Connection {
    private static Properties config = PropertiesUtil.getProperties("MongoDB.properties") ;
    private static int getPort(){
        return Integer.parseInt(config.getProperty("port", "6379")) ;
    }

    private static String getIp(){
        return config.getProperty("ip", "127.0.0.1") ;
    }
    private static String getPassword(){
        return config.getProperty("password", "1qaz!QAZ") ;
    }


}
