package com.example.testapp.database;

import java.sql.Connection;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        connection = ConnectionHelper.createConnectionWithDB();
        return connection;
    }
}
