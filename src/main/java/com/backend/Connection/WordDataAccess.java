package com.backend.Connection;

import com.backend.LocalDictionary.Dictionary.Word;

import java.sql.*;
import java.util.ArrayList;

import static com.backend.Connection.ConnectDatabase.tableEdictName;

public class WordDataAccess implements IDataAccess<Word> {
    private ConnectDatabase connectDatabase;

    public WordDataAccess() {
        connectDatabase = new ConnectDatabase();
    }

    /**
     * The function init new object access to edict from database
     *
     * @return new Obj
     */
    public static WordDataAccess getInstance() {
        return new WordDataAccess();
    }

    /**
     * @param word is the word insert to database
     * @return the number of line is changed
     */
    @Override
    public int insert(Word word) {
        int result = 0;
        try {
            Connection connect = connectDatabase.getConnection();
            String query = "INSERT INTO " + tableEdictName + "(word, detail) VALUES (?, ?)";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, word.getTarget());
            preparedStatement.setString(2, word.getExplain());

            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * The function update the meaning of word.
     *
     * @param word is the word need to update
     * @return the number word is update.
     */
    @Override
    public int update(Word word) {
        int result = 0;
        try {
            Connection connect = connectDatabase.getConnection();
            String query = "UPDATE " + tableEdictName +
                    " SET detail = ?" +
                    " WHERE word = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, word.getExplain());
            preparedStatement.setString(2, word.getTarget());

            result = preparedStatement.executeUpdate();
            connectDatabase.closeConnection(connect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * The function to delete word from database.
     *
     * @param word is the word to delete.
     * @return the number of words deleted.
     */
    @Override
    public int delete(Word word) {
        int result = 0;
        try {
            Connection connect = connectDatabase.getConnection();
            String query = "DELETE FROM " + tableEdictName + " WHERE word = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, word.getTarget());

            result = preparedStatement.executeUpdate();

            connectDatabase.closeConnection(connect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * The function get all word from database.
     *
     * @return ArrayList all word from database.
     */
    @Override
    public ArrayList<Word> selectAll() {
        System.out.println("Start get all word !!!");
        ArrayList<Word> result = new ArrayList<>();
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT * FROM " + tableEdictName;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String target = resultSet.getString("word");
                String detail = resultSet.getString("detail");
                Word tmpWord = new Word(target, detail);
                result.add(tmpWord);
            }

            System.out.println("End get all words!!!");
            connectDatabase.closeConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * The function translate English Word.
     *
     * @param target is the English word need to translate.
     * @return the meaning of word type HTML.
     */
    public String translateWord(String target) {
        String result = "";
        try {
            Connection connect = connectDatabase.getConnection();
            String query = "SELECT detail FROM " + tableEdictName + " WHERE word = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, target);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString("detail");
            }

            connectDatabase.closeConnection(connect);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * The function get list Suggest Word from database.
     *
     * @param currentWord is the current word get from text filed.
     * @return the list Suggest WPrd.
     */
    public ArrayList<String> suggestListWords(String currentWord) {
        ArrayList<String> result = new ArrayList<>();
        try {
            Connection connect = connectDatabase.getConnection();
            String query = "SELECT word FROM " + tableEdictName + " WHERE word LIKE ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, currentWord + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("word"));
            }

            connectDatabase.closeConnection(connect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
