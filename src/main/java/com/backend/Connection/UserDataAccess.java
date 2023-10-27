package com.backend.Connection;

import com.backend.User.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDataAccess extends ConnectDatabase implements IDataAccess<User> {
    private ConnectDatabase connectDatabase;

    /**
     * Construct init new Object UserDataAccess to access database.
     */
    public UserDataAccess() {
        connectDatabase = new ConnectDatabase();
    }

    public static UserDataAccess getInstance() {
        return new UserDataAccess();
    }

    /**
     * When user register new account, function will add new userInfo to database.
     * @param user is new user.
     * @return 1 if user registered successfully.
     */
    @Override
    public int insert(User user) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "INSERT INTO " + tableUser + "(firstname, lastname, username, userpassword) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());

            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * When User login to app, if it is incorrect account, user must be logged in again.
     * @param user is userInfo consist of: username and password.
     * @return true if user is logged in successfully.
     */
    public boolean isCorrectAccount(User user) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT * FROM " + tableUser
                    + " WHERE username = ? AND userpassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = 1;
            }

            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result != 0;
    }

    /**
     * When user forget their password, the function will update new password.
     * @param user is userInfo consist of username and password.
     * @return 1 if user account update new password.
     */
    public int updateAccount(User user){
        int res = 0;

        try {
            Connection connection = connectDatabase.getConnection();
            String query = "UPDATE " + tableUser + " SET " +
                    " userpassword = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getUsername());
            res = preparedStatement.executeUpdate();
            connectDatabase.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * When user play game, scores will be saved and update in database
     * @param user consists of : user name and score 2 games
     * @return 1 if updated score successfully.
     */
    @Override
    public int update(User user) {
        int result = 0;
        try {
            Connection connect = connectDatabase.getConnection();
            String query = "UPDATE " + tableUser +
                    " SET " +
                    "scoregame1 = ?," +
                    "scoregame2 = ? " +
                    "WHERE username = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, user.getScoreGame1());
            preparedStatement.setInt(2, user.getScoreGame2());
            preparedStatement.setString(3, user.getUsername());

            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;

    }

    /**
     *
     * @param username is the username of the user.
     * @return the user with full information.
     */
    public User getUserInfo(String username) {
        User res = new User();
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT * FROM " + tableUser
                    + " WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("userpassword");
                String fName = resultSet.getString("firstname");
                String lName = resultSet.getString("lastname");
                int score1 = resultSet.getInt("scoregame1");
                int score2 = resultSet.getInt("scoregame2");
                res = new User(fName, lName, userName, password, score1, score2);
            }

            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
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
