package com.company;

import java.sql.*;
import java.util.Properties;

public class MariaDB {
    // 用以连接数据库，实现与数据库有关的操作，MariaDB是Mysql的一个自由衍生版本
    private static final String dbClassName = "org.mariadb.jdbc.Driver";
    // Connection string.test是数据库的名字

    private static final String CONNECTION =
            "jdbc:mariadb://localhost:3306/test";

    private static final String password = "123";

    public static ResultSet select(String args) throws
            ClassNotFoundException, SQLException {
        //找不到Class的异常是调试时用的，因为在两台机器上测试
        //检测是否找到数据库的类，是测试代码
        Class.forName(dbClassName);
        // Properties是用户属性
        Properties p = new Properties();
        p.put("user", "root");
        p.put("password", MariaDB.password);
        //开始连接
        Connection c = DriverManager.getConnection(CONNECTION, p);
        Statement statement = c.createStatement();
        ResultSet result = statement.executeQuery(args);
        c.close();
        return result;
    }
    //TODO:增加上次观看的主播的功能
    //TODO:增加最近常看主播的功能
    //public static
}
