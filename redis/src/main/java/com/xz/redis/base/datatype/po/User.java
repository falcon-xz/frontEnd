package com.xz.redis.base.datatype.po;

import com.xz.redis.base.pool.Connection;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-12.
 */
public class User {
    private String id ;
    private String name ;
    private String age ;
    private String sex ;

    public User() {
    }

    public User(String id, String name, String age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<>() ;
        Class cz = User.class ;
        try {

            Field[] fields = cz.getDeclaredFields() ;
            for (Field field:fields) {
                String key = field.getName() ;
                Method method = cz.getDeclaredMethod("get"+key.substring(0,1).toUpperCase()+key.substring(1)) ;
                String value = (String)method.invoke(this) ;
                map.put(key,value) ;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map ;
    }

}
