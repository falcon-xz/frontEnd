package com.xz.common.utils.db.connection;

import com.xz.common.utils.db.DBUtil;
import com.xz.common.utils.db.factory.DBCenter;
import com.xz.common.utils.db.factory.po.DBConfig;

import java.sql.*;
import java.util.Map;

/**
 * falcon -- 2016/11/24.
 */
public class MySqlConnection extends DBCenter{

    public MySqlConnection(Map<String, DBConfig> map) {
        super(map);
        try {
            Class.forName("com.connection.jdbc.Driver") ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("init MySqlConnection");
    }
    @Override
    public Connection connection(String database) {
        DBConfig dbConfig = super.map.get(database) ;
        Connection connection = null ;
        try {
            connection = DriverManager.getConnection(dbConfig.getUrl()+dbConfig.getDatabase(),dbConfig.getUsername(),dbConfig.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  connection ;
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection("ambari") ;
            DatabaseMetaData databaseMetaData = conn.getMetaData() ;
            ResultSet rs = databaseMetaData.getTables(null,null,null,null);
            ResultSetMetaData rsmd = rs.getMetaData() ;
            for(int i = 1;i<=rsmd.getColumnCount();i++){
                System.out.println(rsmd.getColumnName(i));
            }
            while (rs.next()){
                System.out.println(rs.getString("TABLE_NAME"));
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DBUtil.getConnection("hive") ;
            DatabaseMetaData databaseMetaData = conn.getMetaData() ;
            ResultSet rs = databaseMetaData.getTables(null,null,null,null);
            ResultSetMetaData rsmd = rs.getMetaData() ;
            for(int i = 1;i<=rsmd.getColumnCount();i++){
                System.out.println(rsmd.getColumnName(i));
            }
            while (rs.next()){
                System.out.println(rs.getString("TABLE_NAME"));
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
