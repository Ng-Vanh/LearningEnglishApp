package com.backend.Connection;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDatabase {
    protected static final String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_edictionary";
    protected static final String userName = "freedb_ngvanh2234";
    protected static final String passWord = "*S$D&NpvY$V*#a6";
    protected static final String tableEdictName = "freedb_edictionary.tbl_edict";
    protected static final String tableUser = "freedb_edictionary.tbl_user";
    // protected static final String url = "jdbc:mysql://127.0.0.1:3307/edict";
    // protected static final String userName = "root";
    // protected static final String passWord = "220604";
    // protected static final String tableEdictName = "edict.tbl_edict";

    // protected static final String tableUser = "edict.tbl_user";
    public ConnectDatabase() {
    }

    /**
     * The function connect with database.
     *
     * @return The function connect with database.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            // register new driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Init ConnecDatabase !!!");
            // Create connection to database
            connection = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Init successfully!!!");

        } catch (ClassNotFoundException e) {
            //Exception: not found driver
            e.printStackTrace();
        } catch (SQLException e) {
            //Exception: Don't connect to database
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                System.out.println("Close ConnectDatabase !!!");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showInfo(Connection cn){
        if(cn!=null){
            try {
                System.out.println(cn.getMetaData().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
