package com.backend.User;

public class UserLearnWord extends User {
    String topic;
    String word;

    public UserLearnWord() {

    }

    /**
     * The constructor init new object.
     *
     * @param username is the username of the user.
     * @param topic    is the topic user studied.
     * @param word     is the word user studied.
     */
    public UserLearnWord(String username, String topic, String word) {
        super(username);
        this.topic = topic;
        this.word = word;
    }

    public UserLearnWord(String username, String topic) {
        this.topic = topic;
        this.word = username;
    }

    public UserLearnWord(String username) {
        super(username);
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

    /**
     * The function override this method equals.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof UserLearnWord) {
            UserLearnWord ulw = (UserLearnWord) o;
            if (super.getUsername().equals(ulw.getUsername())
                    && this.getTopic().equals(ulw.getTopic())
                    && this.getWord().equals(ulw.getWord())) {
                return true;
            }
        }
        return false;
    }
}
