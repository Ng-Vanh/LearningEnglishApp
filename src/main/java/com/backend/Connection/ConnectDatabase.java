package com.backend.Connection;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDatabase {
    protected static final String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_edictionary";
    protected static final String userName = "freedb_ngvanh2234";
    protected static final String passWord = "*S$D&NpvY$V*#a6";
    protected static final String tableName = "freedb_edictionary.tbl_edict";
    /**
     * The function connect with database.
     *
     * @return The function connect with database.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Đăng ký driver (sử dụng phiên bản mới)
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Tạo các thông số:
//            String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_edictionary";
//            String userName = "freedb_ngvanh2234";
//            String passWord = "*S$D&NpvY$V*#a6";

            // Tạo kết nối
            connection = DriverManager.getConnection(url, userName, passWord);

        } catch (ClassNotFoundException e) {
            // Xử lý ngoại lệ khi không tìm thấy driver
            e.printStackTrace();
        } catch (SQLException e) {
            // Xử lý ngoại lệ khi kết nối không thành công
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ khi đóng kết nối gặp lỗi
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

    /**
     * Test connect to database: show all word from database
     * @param args
     */
    public static void main(String[] args) {
        String curStr = "hi";
        ArrayList<String> tmp = WordDataAccess.getInstance().suggestListWords(curStr);
        for(String s: tmp){
            System.out.println(s);
        }
    }
}
