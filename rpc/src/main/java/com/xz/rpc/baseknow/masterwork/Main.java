package com.xz.rpc.baseknow.masterwork;

import java.util.Map;
import java.util.Set;

/**
 * falcon -- 2016/12/22.
 */
public class Main {
    public static void main(String[] args) {
        Master master = new Master(new PlusWork(),5) ;
        for (int i = 1; i <= 100; i++) {
            master.submit(i);
        }
        master.execute();
        int ret = 0 ;
        Map<String,Object> resultMap = master.getResultMap() ;
        while (resultMap.size()>0||!master.isComplete()){
            Set<String> keys = resultMap.keySet() ;
            String key = null ;
            for (String k:keys){
                key = k ;
                break;
            }
            Integer i = null ;
            if (key!=null){
                i = (Integer) resultMap.get(key) ;
            }
            if (i!=null){
                ret += i ;
            }
            if (key!=null){
                resultMap.remove(key) ;
            }
        }
        System.out.println(ret);
    }
}
