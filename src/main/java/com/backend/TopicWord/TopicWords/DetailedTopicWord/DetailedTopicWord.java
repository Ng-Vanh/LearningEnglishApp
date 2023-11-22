package com.backend.TopicWord.TopicWords.DetailedTopicWord;

import com.backend.TopicWord.Utils.Definition;
import com.backend.TopicWord.Utils.Quiz;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class DetailedTopicWord {
    @JsonProperty("type")
    private String type;

    @JsonProperty("quiz")
    private Quiz quiz;

    @JsonProperty("definition")
    private Definition definition;

    public Definition getDefinition() {
        return definition;
    }

    public DetailedTopicWord() {

    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void main(String[] args) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailedTopicWord detailedTopicWord)) return false;
        return Objects.equals(definition.getTopic(), detailedTopicWord.getDefinition().getTopic()) && Objects.equals(definition.getWord(), detailedTopicWord.getDefinition().getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition.getTopic(), definition.getWord());
    }

    @Override
    public String toString() {
        return definition + "\n" + "---------------------------------" + "\n" + quiz;
    }
}
