package com.xz.common.utils.db.factory;

import com.xz.common.utils.db.exception.DBParseException;
import com.xz.common.utils.properties.PropertiesUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * spring 托管
 * falcon -- 2016/11/24.
 */
public class DBSpringFactory {
    private static Map<String,Properties> map;
    private static DBCenter dbCenter ;

    private DBSpringFactory(){
        System.out.println("初始化 DBSpringFactory");
        String path = "db.properties" ;
        Properties config = PropertiesUtil.getProperties(path) ;
        try {
            String className = config.getProperty("databaseclass") ;
            Class cz = Class.forName(className) ;
            Constructor constructor = cz.getConstructor(Map.class) ;
            map = DBParseUtils.parse(config) ;
            dbCenter = (DBCenter)constructor.newInstance(map) ;
        } catch (DBParseException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(String database){
        return dbCenter.connection(database) ;
    }
}
