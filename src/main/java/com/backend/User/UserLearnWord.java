package com.backend.User;

public class UserLearnWord extends User{
    String topic;
    String word;
    public UserLearnWord(){

    }

    /**
     * The constructor init new object.
     * @param username is the username of the user.
     * @param topic is the topic user studied.
     * @param word is the word user studied.
     */
    public UserLearnWord(String username, String topic, String word){
        super(username);
        this.topic = topic;
        this.word = word;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
