package com.xz.common.utils.db.factory;

import com.xz.common.utils.db.factory.po.DBArgs;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * falcon -- 2016/11/24.
 */
public abstract class DBCenter {
    protected Map<String,Properties> map ;

    public DBCenter(Map<String, Properties> map) {
        this.map = map;
    }
    public abstract Connection connection(String database) ;
}
