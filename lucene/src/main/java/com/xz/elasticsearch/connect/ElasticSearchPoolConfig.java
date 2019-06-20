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
    private String clusterName;
    private String address ;
    private List<HttpHost> list ;

    public ElasticSearchPoolConfig(String clusterName, String address) {
        this.clusterName = clusterName;
        this.address = address;
        list = new ArrayList<>() ;
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
    }

    public String getClusterName() {
        return clusterName;
    }

    public List<HttpHost> getList() {
        return list;
    }
}
