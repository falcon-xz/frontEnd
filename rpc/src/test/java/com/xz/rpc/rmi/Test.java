package com.xz.rpc.rmi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * falcon -- 2016/12/12.
 */
public class Test {
    private Map map = new HashMap() ;
    public void setMap(){
        map.put("1","1") ;
    }

    public Map getMap() {
        return map;
    }

    public static void main(String[] args) {
        Test test = new Test() ;
        test.setMap();
        Iterator it = test.getMap().entrySet().iterator() ;
        while(it.hasNext()){
            Map.Entry s = (Map.Entry)it.next() ;
            System.out.println(s.getKey());
            it.remove();
        }
        Map map2 = test.getMap() ;
        System.out.println(map2.size());
    }
}
