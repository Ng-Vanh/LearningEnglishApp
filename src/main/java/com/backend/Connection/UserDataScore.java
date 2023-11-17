package com.backend.Connection;

import com.backend.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.backend.Connection.ConnectDatabase.tableScoreStatus;

public class UserDataScore implements IDataAccess<User>{
    private ConnectDatabase connectDatabase;
    public UserDataScore(){
        connectDatabase = new ConnectDatabase();
    }
    public static UserDataScore getInstance(){
        return new UserDataScore();
    }
    @Override
    public int insert(User user) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "INSERT INTO " + tableScoreStatus + "(username, curscoregame1, curscoregame2) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, user.getScoreGame1());
            preparedStatement.setInt(3, user.getScoreGame2());

            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(UserDataScore.getInstance().insert(new User("abc123",200,250)));

    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public ArrayList<User> selectAll() {
        return null;
    }
}
