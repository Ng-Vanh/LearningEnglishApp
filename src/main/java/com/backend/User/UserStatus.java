package com.backend.User;

import com.backend.Connection.UserDataAccess;
import com.backend.Connection.UserDataScore;

import static com.example.dictionaryenvi.Account.Login.currentUser;

public class UserStatus extends User {

    private String word;

    /**
     * Constructor initializes new object use for update status word of user.
     *
     * @param userName
     * @param word
     */

    public UserStatus(String userName, String word) {
        super(userName);
        this.word = word;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    /**
     * The function update score after play each game and update max score too database.
     */
    public static void updateScoreStatus(int score1, int score2) {
        if (score1 == -1) {
            User tmp = new User(currentUser.getUsername(), 0, score2);
            UserDataScore.getInstance().insert(tmp);
        } else if (score2 == -1) {
            User tmp = new User(currentUser.getUsername(), score1, 0);
            UserDataScore.getInstance().insert(tmp);
        }
        UserDataAccess.getInstance().updateScore(currentUser.getUsername());
    }

    public static void main(String[] args) {
        updateScoreStatus(-1, 1000);
    }
}
