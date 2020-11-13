package com.company;

import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.DriverManager;

public class MairaDB {
    // 用以连接数据库，MairaDB是Mysql的一个自由衍生版本
    private static final String dbClassName = "org.mariadb.jdbc.Driver";
    // Connection string.test是数据库的名字

    private static final String CONNECTION =
            "jdbc:mariadb://localhost:3306/test";

    private static final String password = "123";
    public static void select(String args) throws
            //找不到Class的异常是调试时用的，因为在两台机器上测试
            ClassNotFoundException, SQLException
    {
        System.out.println(dbClassName);

        Class.forName(dbClassName);
        // Properties for user and password. Here the user and password are both 'paulr'
        Properties p = new Properties();
        p.put("user","root");
        p.put("password", MairaDB.password);
        //开始连接
        Connection c = DriverManager.getConnection(CONNECTION,p);
        System.out.println("It works !");
        c.close();
    }
}
