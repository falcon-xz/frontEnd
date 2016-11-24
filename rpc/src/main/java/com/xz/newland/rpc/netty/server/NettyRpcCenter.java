package com.xz.newland.rpc.netty.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * falcon -- 2016/11/23.
 */
public class NettyRpcCenter {
    private static Map<String, Object> map = new HashMap<>();
    private static ExecutorService executorService = Executors.newCachedThreadPool() ;
    public static void regist(String key, Object value) {
        map.put(key, value);
    }
    public static Object refer(String key) {
        return map.get(key);
    }

    public static void exec(Runnable runnable){
        executorService.submit(runnable) ;
    }
}
