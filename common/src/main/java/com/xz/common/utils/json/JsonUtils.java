package com.xz.common.utils.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * falcon -- 2016/11/27.
 */
public class JsonUtils {
    public static Map json2Map(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.toMap();
    }

    public static List<Object> json2List(String json) {
        JSONArray jsonArray = new JSONArray(json);
        return jsonArray.toList();
    }


    public static void main(String[] args) {
        /*String s = "[{\"path\":1,\"num\":2,\"offset\":3},{\"path\":1,\"num\":2,\"offset\":3}]";
        List<Object> list = JsonUtils.json2List(s);
        for (Object o : list) {
            HashMap map = (HashMap) o;
            System.out.println(map.get("path"));
        }*/
        Test test = new JsonUtils().new Test("1",2,3) ;

        JSONArray jsonArray = new JSONArray() ;
        JSONObject jsonObject1 = new JSONObject() ;
        jsonObject1.put("path","1") ;
        jsonObject1.put("num",2) ;
        jsonObject1.put("offset",3) ;
        JSONObject jsonObject2 = new JSONObject() ;
        jsonObject2.put("path","4") ;
        jsonObject2.put("num",5) ;
        jsonObject2.put("offset",6) ;
        jsonArray.put(jsonObject1) ;
        jsonArray.put(jsonObject2) ;
        System.out.println(jsonArray.toString());
    }

    class Test {
        private String path;
        private int num;
        private long offset;

        public Test(String path, int num, long offset) {
            this.path = path;
            this.num = num;
            this.offset = offset;
        }

        public String getPath() {
            return path;
        }

        public int getNum() {
            return num;
        }

        public long getOffset() {
            return offset;
        }
    }
}
