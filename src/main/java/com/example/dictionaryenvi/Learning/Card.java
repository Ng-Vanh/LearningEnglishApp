package com.example.dictionaryenvi.Learning;

import org.apache.poi.ss.usermodel.Cell;

public class Card {
    private String topic;
    private String word;
    private String example;
    private String type;
    private String pronounce;
    private String explain;
    private boolean isLearnWord;

    public boolean isLearnWord() {
        return isLearnWord;
    }

    public void setLearnWord(boolean learnWord) {
        isLearnWord = learnWord;
    }

    public Card(String word , String topic , String type , String example , String pronounce , String explain , boolean isLearnWord) {
        this.word = word;
        this.topic = topic;
        this.type = type;
        this.example = example;
        this.pronounce = pronounce;
        this.explain = explain;
        this.isLearnWord = isLearnWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public String getType() {
        return type;
    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
