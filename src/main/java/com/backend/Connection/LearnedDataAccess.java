package com.backend.Connection;

import com.backend.User.UserLearnWord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.backend.Connection.ConnectDatabase.*;

public class LearnedDataAccess implements IDataAccess<UserLearnWord>{
    private ConnectDatabase connectDatabase;
    public LearnedDataAccess(){
        connectDatabase = new ConnectDatabase ();
    }
    public static LearnedDataAccess getInstance(){
        return new LearnedDataAccess();
    }

    /**
     * The function
     * @param userLearnWord
     * @return
     */
    @Override
    public int insert(UserLearnWord userLearnWord) {
        int result = 0;
        try{
            Connection connection = connectDatabase.getConnection();
            String query = "INSERT INTO " + tableLearning + "(username, topic, word) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userLearnWord.getUsername());
            preparedStatement.setString(2, userLearnWord.getTopic());
            preparedStatement.setString(3,userLearnWord.getWord());


            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int update(UserLearnWord userLearnWord) {
        return 0;
    }

    @Override
    public int delete(UserLearnWord userLearnWord) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "DELETE FROM " + tableLearning + " WHERE word = ? and username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userLearnWord.getWord());
            preparedStatement.setString(2,userLearnWord.getUsername());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }
    public int countLearnedWord(String username, String topic){
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT COUNT(*) FROM " + tableLearning + " WHERE username = ? AND topic = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, topic);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public ArrayList<UserLearnWord> selectAll() {
        return null;
    }
}
