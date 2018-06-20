package com.xz.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by xz on 2018/6/20.
 */
public class JacksonUtil {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        String s = "{\"errcode\":40018,\"errmsg\":\"invalid button name size\"}";
        try {
            JsonNode jsonNode = mapper.readTree(s);
            System.out.println(jsonNode.has("errcode"));
            System.out.println(jsonNode.findValue("errcode"));

            Ret ret = mapper.readValue(s,Ret.class) ;
            System.out.println(ret);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
