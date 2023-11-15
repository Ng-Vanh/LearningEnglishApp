package com.backend.User;

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
}
