package com.xz.elasticsearch.connect;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by xz on 2018/12/28.
 */
public class ElasticSearchPoolConfig extends GenericObjectPoolConfig<RestHighLevelClient> {
    private static Pattern fenPattern = Pattern.compile(";") ;
    private static Pattern maoPattern = Pattern.compile(":") ;
    private String address ;
    private boolean authentication ;
    private String username ;
    private String password ;
    private HttpHost[] httpHosts ;

    public ElasticSearchPoolConfig(String hosts, boolean authentication, String username, String password) {
        this.address = hosts;
        this.authentication = authentication;
        this.username = username;
        this.password = password;
        List<HttpHost> list = new ArrayList<>() ;
        String[] strings = fenPattern.split(address) ;
        for (String hostPost:strings) {
            String[] s = maoPattern.split(hostPost) ;
            HttpHost httpHost = null;
            try {
                httpHost = new HttpHost(s[0],Integer.valueOf(s[1]),"http");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            list.add(httpHost) ;
        }
        httpHosts = list.toArray(new HttpHost[list.size()]);
    }

    public HttpHost[] getHttpHosts() {
        return httpHosts;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
