package com.backend.TopicWord.TopicWords.SimpleTopicWord;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SimpleTopicWord {
    private String topic;
    private String word;

    public SimpleTopicWord() {

    }

    public SimpleTopicWord(String topic, String word) {
        this.topic = topic;
        this.word = word;
    }

    public String getTopic() {
        return topic;
    }

    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleTopicWord that)) return false;
        return Objects.equals(word, that.getWord()) && Objects.equals(topic, that.getTopic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, topic);
    }

    @Override
    public String toString() {
        return    "Topic: " + topic + "\n"
                + "Word: " + word;
    }
}
