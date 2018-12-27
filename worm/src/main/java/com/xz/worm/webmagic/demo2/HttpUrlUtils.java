package com.xz.worm.webmagic.demo2;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xz on 2018/12/23.
 */
public class HttpUrlUtils {

    /**
     * 从链接中获取工程前缀路径
     *
     * @param url http://xx.cc.vv/ttt/ttt
     * @return http://xx.cc.vv/
     */
    public static String getDomain(URL url) {
        StringBuilder sb = new StringBuilder();
        sb.append(url.getProtocol()).append("://").append(url.getHost());
        return sb.toString();
    }

}
