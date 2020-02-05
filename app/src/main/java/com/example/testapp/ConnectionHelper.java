package com.example.testapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.testapp.DBConnectionConstants.DBName;
import static com.example.testapp.DBConnectionConstants.DBpassword;
import static com.example.testapp.DBConnectionConstants.DBusername;
import static com.example.testapp.DBConnectionConstants.IPv4;
import static com.example.testapp.DBConnectionConstants.JdbcDriver;

class ConnectionHelper {

    @SuppressLint("NewApi")
    static Connection createConnectionWithDB(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try {
            Class.forName(JdbcDriver);
            connectionURL = "jdbc:jtds:sqlserver://" + IPv4 + ";"
                    + "databaseName=" + DBName + ";user=" + DBusername + ";password="
                    + DBpassword + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return connection;
    }
}
