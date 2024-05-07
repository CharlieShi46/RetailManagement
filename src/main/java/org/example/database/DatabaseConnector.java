package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5704279";
    private static final String USER = "sql5704279";
    private static final String PASSWORD = "bDS7q6d3FM";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
