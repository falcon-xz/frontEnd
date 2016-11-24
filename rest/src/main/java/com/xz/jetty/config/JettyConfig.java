package com.xz.jetty.config;

import com.xz.frontEnd.utils.PropertiesUtil;

import java.util.Properties;

/**
 * falcon -- 2016/11/22.
 */
public class JettyConfig {
    private static Properties config = PropertiesUtil.getProperties("jetty/jetty.properties") ;

    public int getPort(){
        return Integer.parseInt(config.getProperty("port", "4444")) ;
    }

    public String getHost(){
        return config.getProperty("host", "xz") ;
    }
}
