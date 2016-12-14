package com.xz.rpc.baseknow.concurrent;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * falcon -- 2016/12/14.
 */
public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {

        ConcurrentSkipListMap<Integer,String> map = new ConcurrentSkipListMap() ;
        for (int i = 0; i < 5; i++) {
            map.put(i,""+i) ;
        }
        System.out.println(map.firstKey());

    }
}
