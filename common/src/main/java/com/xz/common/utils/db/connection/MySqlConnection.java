package com.xz.common.utils.db.connection;

import com.xz.common.utils.db.factory.DBCenter;
import com.xz.common.utils.db.factory.po.DBArgs;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * falcon -- 2016/11/24.
 */
public class MySqlConnection extends DBCenter{

    public MySqlConnection(Map<String, Properties> map) {
        super(map);
        try {
            Class.forName("com.mysql.jdbc.Driver") ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("init MySqlConnection");
    }
    @Override
    public Connection connection(String database) {
        Properties properties = super.map.get(database) ;
        String databasePlus = database+"." ;
        Connection connection = null ;
        try {
            connection = DriverManager.getConnection(properties.getProperty(databasePlus+DBArgs.url)+database,properties.getProperty(databasePlus+DBArgs.username),properties.getProperty(databasePlus+DBArgs.password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  connection ;
    }

}
