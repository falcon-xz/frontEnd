package com.xz.common.utils.db.connection;

import com.xz.common.utils.db.factory.DBCenter;
import com.xz.common.utils.db.factory.po.DBArgs;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * falcon -- 2016/11/24.
 */
public class DbcpConnection extends DBCenter {
    private Map<String,BasicDataSource> basicDataSourceMap ;
    public DbcpConnection(Map<String, Properties> map) {
        super(map);
        basicDataSourceMap = new HashMap<>() ;
        for (Map.Entry<String, Properties> entry:map.entrySet()) {
            String database = entry.getKey() ;
            String databasePlus = database+"." ;
            Properties properties = entry.getValue() ;
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
            basicDataSource.setUrl(properties.getProperty(databasePlus+DBArgs.url)+database);
            basicDataSource.setUsername(properties.getProperty(databasePlus+DBArgs.username));
            basicDataSource.setPassword(properties.getProperty(databasePlus+DBArgs.password)) ;

            basicDataSource.setInitialSize(Integer.parseInt(properties.getProperty(databasePlus+DBArgs.initialsize,"10")));
            basicDataSource.setMinIdle(Integer.parseInt(properties.getProperty(databasePlus+DBArgs.minidle,"1")));
            basicDataSource.setMaxActive(Integer.parseInt(properties.getProperty(databasePlus+DBArgs.maxactive,"30")));
            basicDataSource.setMaxWait(Long.parseLong(properties.getProperty(databasePlus+DBArgs.maxwait,"60000")));

            basicDataSourceMap.put(entry.getKey(),basicDataSource) ;
        }
    }

    @Override
    public Connection connection(String database) {
        BasicDataSource basicDataSource = basicDataSourceMap.get(database) ;
        Connection connection = null ;
        try {
            connection = basicDataSource.getConnection() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
