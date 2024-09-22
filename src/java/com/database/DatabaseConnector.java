package com.database;

import com.mysql.cj.jdbc.MysqlDataSource;


import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {


    private static MysqlDataSource dataSource = new MysqlDataSource();

    public static void connect() {
        dataSource.setURL(CONN_STRING);
    }


    public static Connection connection() throws SQLException {
        return dataSource.getConnection(username, password);
    }

}
