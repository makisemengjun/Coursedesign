package com.company;

import java.sql.*;
import java.util.Properties;

public class MariaDB {
    // 用以连接数据库，实现与数据库有关的操作，MariaDB是Mysql的一个自由衍生版本
    private static final String dbClassName =
            "org.mariadb.jdbc.Driver";

    // Connection string.test是数据库的名字
    private static final String CONNECTION =
            "jdbc:mariadb://localhost:3306/test";

    //更改使用的数据库名字以及帐号密码要在源码里修改
    private static final String user = "root";
    private static final String password = "123";

    public static ResultSet select(String args)
            throws ClassNotFoundException, SQLException {
        //检测是否找到数据库驱动
        Class.forName(dbClassName);
        // Properties是用户属性
        Properties p = new Properties();
        p.put("user", MariaDB.user);
        p.put("password", MariaDB.password);

        //开始连接
        Connection c = DriverManager.getConnection(CONNECTION, p);
        Statement statement = c.createStatement();
        //执行查询
        ResultSet result = statement.executeQuery(args);
        c.close();
        return result;
    }

}
