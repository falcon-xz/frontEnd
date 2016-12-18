package com.xz.common.utils.reflect;

import com.xz.common.utils.db.connection.DbcpConnection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * falcon -- 2016/12/16.
 */
public class ObjectUtils {
    public static <T> String println(T t){
        if (t==null){
            return null ;
        }
        StringBuilder sb = new StringBuilder() ;
        Class<?> cz = t.getClass() ;
        sb.append(cz.getName()).append("{\r\n");
        Field[] fields = cz.getDeclaredFields() ;
        for (Field field:fields) {
            try {
                field.setAccessible(true);
                sb.append(field.getName()).append(":").append(field.get(t)).append(",\r\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}") ;

        return sb.toString() ;
    }

    public static void main(String[] args) {
        Map<String, Properties> map = new HashMap<>() ;
        map.put("1",null) ;
        map.put("2",null) ;
        DbcpConnection dbcpConnection = new DbcpConnection(map) ;
        System.out.println(ObjectUtils.println(dbcpConnection));
    }
}
