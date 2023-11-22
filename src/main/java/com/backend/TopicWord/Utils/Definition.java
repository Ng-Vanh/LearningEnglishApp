package com.backend.TopicWord.Utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Definition {
    @JsonIgnore
    private String topic;

    private String word;
    private String type;
    private String phonetic;
    private String example;
    private String explain;
    private String translatedExample;

    @JsonIgnore
    private String audioLink;

    public Definition() {

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getTranslatedExample() {
        return translatedExample;
    }

    public void setTranslatedExample(String translatedExample) {
        this.translatedExample = translatedExample;
    }

    @Override
    public String toString() {
        return "word: " + word + "\n"
                + "explain: " + explain + "\n"
                + "type: " + type + "\n"
                + "topic: " + topic + "\n"
                + "phonetic: " + phonetic + "\n"
                + "example: " + example + "\n"
                + "translatedExample: " + translatedExample + "\n"
                + "audioLink: " + audioLink;
    }
}
