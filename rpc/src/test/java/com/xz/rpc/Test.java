package com.xz.rpc;

import com.xz.common.utils.reflect.ObjectUtils;
import com.xz.rpc.rpc.info.po.RequestNoSer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * falcon -- 2016/12/16.
 */
public class Test {
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>() ;
        map.put("1",null) ;
    }
}
