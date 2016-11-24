package com.xz.frontEnd.utils.db.exception;

import java.sql.SQLException;

/**
 * falcon -- 2016/11/24.
 */
public class DBParseException extends SQLException {
    public DBParseException(String msg){
        super(msg);
    }
}
