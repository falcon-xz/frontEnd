package com.xz.redis.base.datatype.string;

import com.xz.redis.base.pool.Connection;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2017-7-12.
 */
public class User {
    private String name ;
    private String age ;
    private String sex ;

    public User() {
    }

    public User(String name, String age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
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
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
