package com.backend.Connection;

import com.backend.LocalDictionary.Dictionary.Word;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WordDataAccess {
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
    public int insert(Word word) {
        int result = 0;
        try {
            //: Tao ket noi toi csdl
            Connection connect = ConnectDatabase.getConnection();
            //: Tao Statement: moi lien ket toi database
            Statement statement = connect.createStatement();
            //:Tao cau lenh SQL
            String query = "INSERT INTO freedb_edictionary.tbl_edict(word, detail) " +
                    "VALUES ('" + word.getTarget() + "', '" + word.getExplain() + "')";

            result = statement.executeUpdate(query);

            ConnectDatabase.closeConnection(connect);

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
    public int update(Word word) {
        int result = 0;
        try {
            //: Tao ket noi toi csdl
            Connection conect = ConnectDatabase.getConnection();
            //: Tao Statement lien ket voi database
            Statement statement = conect.createStatement();
            //:Tao cau lenh SQL
            String query = "UPDATE freedb_edictionary.tbl_edict" +
                    " SET " +
                    " detail = '" + word.getExplain() + "'" +
                    " WHERE word = '" + word.getTarget() + "'";

            result = statement.executeUpdate(query);
            ConnectDatabase.closeConnection(conect);

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
    public ArrayList<Word> getAllWord() {
        ArrayList<Word> result = new ArrayList<>();
        try {
            Connection conect = ConnectDatabase.getConnection();
            Statement statement = conect.createStatement();
            String query = "SELECT * FROM freedb_edictionary.tbl_edict";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String target = resultSet.getString("word");
                String detail = resultSet.getString("detail");
                Word tmpWord = new Word(target, detail);
                result.add(tmpWord);
            }
            ConnectDatabase.closeConnection(conect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * The function translate English Word.
     * @param target is the English word need to translate.
     * @return the meaning of word type HTML
     */
    public String translateWord(String target) {
        String result = "";
        try {
            Connection conect = ConnectDatabase.getConnection();
            Statement statement = conect.createStatement();
            String query = "SELECT * FROM freedb_edictionary.tbl_edict WHERE word='" + target + "'";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getString("detail");
            }
            ConnectDatabase.closeConnection(conect);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
