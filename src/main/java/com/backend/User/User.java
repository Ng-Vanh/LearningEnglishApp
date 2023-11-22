package com.backend.User;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int scoreGame1;

    public User() {
    }
    public User(String username){
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(String username, int scoreGame1) {
        this.username = username;
        this.scoreGame1 = scoreGame1;
    }

    public User(String firstName, String lastName, String username, String password, int scoreGame1) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.scoreGame1 = scoreGame1;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScoreGame1() {
        return scoreGame1;
    }

    public void setScoreGame1(int scoreGame1) {
        this.scoreGame1 = scoreGame1;
    }


}
