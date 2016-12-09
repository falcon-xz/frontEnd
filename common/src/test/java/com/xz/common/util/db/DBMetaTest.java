package com.xz.common.util.db;

import com.xz.common.utils.db.DBUtil;

import java.sql.*;

/**
 * falcon -- 2016/12/1.
 */
public class DBMetaTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection("test");
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, null, null);
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i));
            }
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
