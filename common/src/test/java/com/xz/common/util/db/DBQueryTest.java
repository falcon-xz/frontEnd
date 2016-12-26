package com.xz.common.util.db;

import com.xz.common.utils.db.DBUtil;

import java.sql.*;

/**
 * falcon -- 2016/12/1.
 */
public class DBQueryTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.getConnection("ambari");
            PreparedStatement pstm = conn.prepareStatement("select ? from dual") ;
            pstm.setString(1,"2");
            ResultSet rs = pstm.executeQuery() ;
            while (rs.next()){
                System.out.println(rs.getString(1)) ;
            }
            System.out.println(pstm.toString());
            rs.close();
            pstm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
