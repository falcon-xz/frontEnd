package com.xz.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by xz on 2019/7/9.
 */
public class MapTeat {
    public static void main(String[] args) {
        Map<Integer,StringBuilder> map=new HashMap<Integer,StringBuilder>();
        map.put(1,new StringBuilder("c1"));
        map.put(2,new StringBuilder("c2"));
        map.put(3,new StringBuilder("c3"));

        Map<Integer,StringBuilder> unmodifiableMap= Collections.unmodifiableMap(map);
        //这时候如果再往unmodifiableMap中添加元素，会发生错误
        //unmodifiableMap.put(4,new StringBuilder("c4"));

        unmodifiableMap.get(3).append("new");
        System.out.println(unmodifiableMap.get(3));
        System.out.println(map.get(3));


    }
}
