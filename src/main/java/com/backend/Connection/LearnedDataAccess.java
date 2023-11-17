package com.backend.Connection;

import com.backend.User.UserLearnWord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param userLearnWord is the object userLearnWord.
     * @return returns the number of modify lines in the database.
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
    public int insertAll(List<UserLearnWord> usersList){
        int result = 0;
        try{
            Connection connection = connectDatabase.getConnection();
            String value = "";
            for(int i = 0; i < usersList.size() - 1; i++){
                value += "(?, ?, ?),\n";
            }
            value += "(?, ?, ?)";
            String query = "INSERT INTO " + tableLearning + "(username, topic, word) " +
                    "VALUES " + value;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for(int i = 0; i < usersList.size(); i++){
                preparedStatement.setString(i*3 + 1,usersList.get(i).getUsername());
                preparedStatement.setString(i*3 + 2,usersList.get(i).getTopic());
                preparedStatement.setString(i*3 + 3,usersList.get(i).getWord());
            }
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

            connectDatabase.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }
    public int countLearnedWord(String username, String topic){
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT COUNT(DISTINCT word) FROM " + tableLearning + " WHERE username = ? AND topic = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, topic);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }

    public boolean isLearnedWord(UserLearnWord userLearnWord){
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT * FROM " + tableLearning + " WHERE word = ? and username = ? and topic = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userLearnWord.getWord());
            preparedStatement.setString(2, userLearnWord.getUsername());
            preparedStatement.setString(3, userLearnWord.getTopic());

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean learned = resultSet.next();

            connectDatabase.closeConnection(connection);

            return learned;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public ArrayList<UserLearnWord> selectAll() {
        return null;
    }
    public ArrayList<UserLearnWord> getLearnedListWord(String username, String topic) {
        ArrayList<UserLearnWord> result = new ArrayList<>();
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT DISTINCT word FROM " + tableLearning + " WHERE username = ? and topic = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,topic);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("word");
                result.add(new UserLearnWord(username,topic, word));
            }
            connectDatabase.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }
    public Map<String, Integer> listCountLearnedWords(String username){
        Map<String, Integer> result = new HashMap<>();
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT topic, COUNT(DISTINCT word) as total FROM "
                    + tableLearning + " WHERE username = ? GROUP BY topic ORDER BY topic ASC ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String topic = resultSet.getString(1);
                int count = resultSet.getInt(2);
                result.put(topic, count);
            }
            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }
}
