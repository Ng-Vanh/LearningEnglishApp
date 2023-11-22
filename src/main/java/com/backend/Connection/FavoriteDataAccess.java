package com.backend.Connection;

import com.backend.User.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.backend.Connection.ConnectDatabase.tableFavorite;

public class FavoriteDataAccess implements IDataAccess<UserStatus> {
    private ConnectDatabase connectDatabase;

    public FavoriteDataAccess() {
        connectDatabase = new ConnectDatabase();
    }

    public static FavoriteDataAccess getInstance() {
        return new FavoriteDataAccess();
    }

    /**
     * The function inserts a new favorite to the database.
     *
     * @param userStatus is the user status.
     * @return
     */
    @Override
    public int insert(UserStatus userStatus) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "INSERT INTO " + tableFavorite + "(username, word) " +
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

    /**
     * The function gets list favorite words from database to show scene.
     *
     * @param username is current username.
     */
    public List<String> getFavoriteWords(String username) {
        List<String> result = new ArrayList<String>();
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT DISTINCT * FROM " + tableFavorite + " WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("word"));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }

    /**
     * The function removes favorite words from database.
     */
    @Override
    public int delete(UserStatus userStatus) {
        int result = 0;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "DELETE FROM " + tableFavorite + " WHERE word = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userStatus.getWord());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return result;
    }

    /**
     * The function checks word is favorite words ?
     */
    public boolean isFavoriteWord(UserStatus userStatus) {
        boolean isFavorite = false;
        try {
            Connection connection = connectDatabase.getConnection();
            String query = "SELECT * FROM " + tableFavorite + " WHERE word = ? AND username = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userStatus.getWord());
            preparedStatement.setString(2, userStatus.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();
            isFavorite = resultSet.next(); // Kiểm tra xem có kết quả nào trong ResultSet hay không

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isFavorite;
    }

    @Override
    public ArrayList<UserStatus> selectAll() {
        return null;
    }

    @Override
    public int update(UserStatus userStatus) {
        return 0;
    }
}
