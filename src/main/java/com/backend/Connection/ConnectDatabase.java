package com.backend.Connection;

import java.sql.*;

public class ConnectDatabase {
    /**
     * The function connect with database
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Đăng ký driver (sử dụng phiên bản mới)
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Tạo các thông số:
            String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_edictionary";
            String userName = "freedb_ngvanh2234";
            String passWord = "*S$D&NpvY$V*#a6";

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
        Connection connection = ConnectDatabase.getConnection();
        try {
            Statement statement = connection.createStatement();
            // tao cac cau lenh
            String query = "SELECT * FROM freedb_edictionary.tbl_edict";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                int ind = resultSet.getInt("idx");
                String word = resultSet.getString("word");
                String detail = resultSet.getString("detail");
                System.out.println(ind+" - " +word + " - " + detail);
            }
            ConnectDatabase.closeConnection(connection);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
