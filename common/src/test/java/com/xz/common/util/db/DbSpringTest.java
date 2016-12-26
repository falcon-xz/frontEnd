package com.xz.common.util.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.xz.common.utils.db.factory.DBFactory;
import com.xz.common.utils.db.factory.DBSpringFactory;
import com.xz.spring.SpringContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * falcon -- 2016/12/26.
 */
public class DbSpringTest {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring/common.xml") ;
        DBSpringFactory dBSpringFactory = SpringContextHolder.getBean("dBSpringFactory");
        System.out.println(dBSpringFactory.getConnection("hive").getClass());

        DruidDataSource druidDataSource = SpringContextHolder.getBean("hive") ;
        System.out.println(druidDataSource.getClass());
    }
}
