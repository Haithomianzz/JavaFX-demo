package com.database;

import com.mysql.cj.jdbc.MysqlDataSource;


import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/healthcare";
    private final static String username = "root";
    private  static final  String password = "Omar2005Helwa";
    private static MysqlDataSource dataSource = new MysqlDataSource();

    public static void connect() {
        dataSource.setURL(CONN_STRING);
    }


    public static Connection connection() throws SQLException {
        return dataSource.getConnection(username, password);
    }

}
