package com.xz.rpc.rpc.info;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcCenter {
    private Map<String, Object> map;

    public RpcCenter() {
        map = new HashMap<>();
    }

    public void regist(String key, Object value) {
        map.put(key, value);
    }

    public Object refer(String key) {
        return map.get(key);
    }

}
