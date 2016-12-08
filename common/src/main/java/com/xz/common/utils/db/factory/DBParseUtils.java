package com.xz.common.utils.db.factory;

import com.xz.common.utils.db.exception.DBParseException;
import com.xz.common.utils.db.factory.po.DBArgs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * falcon -- 2016/11/24.
 */
public class DBParseUtils {
    public static Pattern pattern = Pattern.compile(" ") ;

    public static Map<String,Properties> parse(Properties allProperties) throws DBParseException{
        Map<String,Properties> map = new HashMap<>() ;
        //获得需要使用连接池的 database
        String databaseNames = allProperties.getProperty(DBArgs.database) ;
        String[] databases = pattern.split(databaseNames) ;

        for (String database:databases) {
            Properties properties = new Properties() ;
            map.put(database,properties) ;
        }
        if (map.size()==0){
            throw new DBParseException("配置文件为空") ;
        }
        for (Map.Entry entry:allProperties.entrySet()) {
            String key = (String)entry.getKey() ;
            if (key.indexOf(".")<0){
                continue;
            }
            String database = key.substring(0,key.indexOf(".")) ;
            String value = (String)entry.getValue() ;
            map.get(database).put(key,value) ;
        }
        return map ;
    }
}
