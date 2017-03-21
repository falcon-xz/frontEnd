package com.xz.spring.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.xz.spring.SpringContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * falcon -- 2017/1/4.
 */
public class ConnectionTest {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring/ApplicationContext.xml") ;
        DruidDataSource druidDataSource = SpringContextHolder.getBean("mysql") ;
        try {
            Connection conn = druidDataSource.getConnection().getConnection() ;
            PreparedStatement pstm = conn.prepareStatement("select Host from user") ;
            ResultSet rs = pstm.executeQuery() ;
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
            rs.close();
            pstm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
