package com.backend.User;

import com.backend.Connection.UserDataAccess;
import com.backend.Connection.UserDataScore;

public class UserStatus extends User {

    private String word;

    /**
     * Constructor initializes new object use for update status word of user.
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
    public static void updateScoreStatus(String username, int score1, int score2) {
        if(score1 == -1){
            User tmp = new User(username,0,score2);
            UserDataScore.getInstance().insert(tmp);
        }else if(score2 == -1){
            User tmp = new User(username,score1,0);
            UserDataScore.getInstance().insert(tmp);
        }
        UserDataAccess.getInstance().updateScore(username);
    }

    public static void main(String[] args) {
        updateScoreStatus("abc123",-1,1000);
    }
}
