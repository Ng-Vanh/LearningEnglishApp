package com.backend.User;

public class UserStatus {
    private String userName;
    private String word;

    public UserStatus(String userName, String word) {
        this.userName = userName;
        this.word = word;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
