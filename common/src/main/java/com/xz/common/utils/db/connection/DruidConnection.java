package com.xz.common.utils.db.connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.xz.common.utils.db.factory.DBCenter;
import com.xz.common.utils.db.factory.po.DBArgs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * falcon -- 2016/12/6.
 */
public class DruidConnection extends DBCenter {
    private Map<String,DruidDataSource> basicDataSourceMap ;
    public DruidConnection(Map<String, Properties> map) {
        super(map);
        basicDataSourceMap = new HashMap<>() ;
        for (Map.Entry<String, Properties> entry:map.entrySet()) {
            String database = entry.getKey() ;
            String databasePlus = database+"." ;
            Properties properties = entry.getValue() ;
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUrl(properties.getProperty(databasePlus+DBArgs.url)+database);
            druidDataSource.setUsername(properties.getProperty(databasePlus+DBArgs.username));
            druidDataSource.setPassword(properties.getProperty(databasePlus+DBArgs.password)) ;

            druidDataSource.setInitialSize(Integer.parseInt(properties.getProperty(databasePlus+DBArgs.initialsize,"10")));
            druidDataSource.setMinIdle(Integer.parseInt(properties.getProperty(databasePlus+DBArgs.minidle,"1")));
            druidDataSource.setMaxActive(Integer.parseInt(properties.getProperty(databasePlus+DBArgs.maxactive,"20")));
            druidDataSource.setMaxWait(Long.parseLong(properties.getProperty(databasePlus+DBArgs.maxwait,"60000")));

            //隔多久进行检测，检测需要关闭的链接 ms
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
            //配置一个链接在池中最小生存时间 ms
            druidDataSource.setMinEvictableIdleTimeMillis(300000);
            //验证sql
            druidDataSource.setValidationQuery("SELECT 'x'");
            druidDataSource.setTestWhileIdle(true);
            druidDataSource.setTestOnBorrow(false);
            druidDataSource.setTestOnReturn(false);

            basicDataSourceMap.put(entry.getKey(),druidDataSource) ;
        }
    }

    @Override
    public Connection connection(String database) {
        DruidDataSource basicDataSource = basicDataSourceMap.get(database) ;
        Connection connection = null ;
        try {
            connection = basicDataSource.getConnection() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
