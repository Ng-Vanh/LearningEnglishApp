package com.backend.Connection;

import com.backend.User.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.backend.Connection.ConnectDatabase.tableHistory;

public class HistoryDataAccess implements IDataAccess<UserStatus>{
    private ConnectDatabase connectDatabase;

    /**
     * Constructor init new object HistoryDataAccess
     */
    public HistoryDataAccess(){
        connectDatabase = new ConnectDatabase();
    }
    public static HistoryDataAccess getInstance(){
        return new HistoryDataAccess();
    }
    @Override
    public int insert(UserStatus userStatus) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "INSERT INTO " + tableHistory + "(username, word) " +
                    "VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userStatus.getUsername());
            preparedStatement.setString(2, userStatus.getWord());


            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<String> getHistory(String username) {
        List<String> result = new ArrayList<String>();
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT DISTINCT * FROM " + tableHistory + " WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("word"));
            }
            connectDatabase.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public int update(UserStatus userStatus) {
        return 0;
    }

    @Override
    public int delete(UserStatus userStatus) {
        return 0;
    }

    @Override
    public ArrayList<UserStatus> selectAll() {
        return null;
    }

    public static void main(String[] args) {
        List<String> li = HistoryDataAccess.getInstance().getHistory("abc123");
        for(String tmp: li){
            System.out.println(tmp);
        }
    }
}
