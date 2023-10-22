package com.backend.Connection;

import com.backend.LocalDictionary.Dictionary.Word;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.backend.Connection.ConnectDatabase.tableEdictName;

public class WordDataAccess extends ConnectDatabase implements IDataAccess<Word>{
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
            //: Tao ket noi toi csdl
            Connection connect = connectDatabase.getConnection();
            //: Tao Statement: moi lien ket toi database
            Statement statement = connect.createStatement();
            //:Tao cau lenh SQL
            String query = "INSERT INTO " + tableEdictName + "(word, detail) " +
                    "VALUES ('" + word.getTarget() + "', '" + word.getExplain() + "')";

            result = statement.executeUpdate(query);

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
            //: Tao ket noi toi csdl
            Connection conect = connectDatabase.getConnection();
            //: Tao Statement lien ket voi database
            Statement statement = conect.createStatement();
            //:Tao cau lenh SQL
            String query = "UPDATE " + tableEdictName +
                    " SET " +
                    " detail = '" + word.getExplain() + "'" +
                    " WHERE word = '" + word.getTarget() + "'";

            result = statement.executeUpdate(query);
            connectDatabase.closeConnection(conect);

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
            Statement statement = connect.createStatement();
            String query = "DELETE FROM " + tableEdictName + " WHERE word='" + word + "'";

            result = statement.executeUpdate(query);
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
            Connection conect = connectDatabase.getConnection();
            Statement statement = conect.createStatement();
            String query = "SELECT * FROM " + tableEdictName;

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String target = resultSet.getString("word");
                String detail = resultSet.getString("detail");
                Word tmpWord = new Word(target, detail);
                result.add(tmpWord);
            }
            System.out.println("End get all words!!!");
            connectDatabase.closeConnection(conect);

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
            Connection conect = connectDatabase.getConnection();
            Statement statement = conect.createStatement();
            String query = "SELECT * FROM " + tableEdictName + " WHERE word='" + target + "'";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getString("detail");
            }
            connectDatabase.closeConnection(conect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * The functiton get list Suggest Word from database.
     *
     * @param currentWord is the current word get from text filed.
     * @return the list Suggest WPrd.
     */
    public ArrayList<String> suggestListWords(String currentWord) {
        ArrayList<String> result = new ArrayList<>();
        try {
            Connection conect = connectDatabase.getConnection();
            Statement statement = conect.createStatement();
            String query = "SELECT * FROM " + tableEdictName + " WHERE word LIKE '" + currentWord + "%'";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(resultSet.getString("word"));
            }
            connectDatabase.closeConnection(conect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
