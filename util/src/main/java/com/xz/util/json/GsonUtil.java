package com.xz.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Created by xz on 2018/6/20.
 */
public class GsonUtil {



    public static void main(String[] args) {
        Gson gson = new Gson() ;
        String s = "{\"errcode\":40018,\"errmsg\":\"invalid button name size\"}";
        JsonElement jsonElement = gson.toJsonTree(s) ;
    }
}
