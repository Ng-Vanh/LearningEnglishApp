package com.backend.Exercise.Exercises.Dictation;

import com.backend.Exercise.Utils.Exercise;
import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;
import com.backend.TopicWord.Utils.Description;
import com.fasterxml.jackson.annotation.*;

@JsonTypeName("Dictation")
public class DictationDescription extends Description {
    @JsonProperty("type")
    private String type = "Dictation";

    @JsonProperty("sentence")
    private String sentence;

    @JsonProperty("sentenceWithBlank")
    private String sentenceWithBlank;

    @JsonProperty("wordBlank")
    private String wordBlank;

    @JsonProperty("translation")
    private String translation;

    @JsonIgnore
    GoogleTranslate googleTranslate = new GoogleTranslate();

    public DictationDescription() {
        super();
    }

    public DictationDescription(String sentence, String sentenceWithBlank, String wordBlank, String translation) {
        super();
        this.sentence = sentence;
        this.sentenceWithBlank = sentenceWithBlank;
        this.wordBlank = wordBlank;
        this.translation = translation;
    }

    public DictationDescription(Exercise exercise) {
        super();
        if (exercise instanceof Dictation) {
            Dictation dictation = (Dictation) exercise;
            GoogleTranslate googleTranslate = new GoogleTranslate();
            this.sentence = dictation.getSentence();
            this.sentenceWithBlank = dictation.getSentenceWithBlank();
            this.wordBlank = dictation.getWordBlank();
            this.translation = "DITME";
        }
    }

    public Dictation getDictation(DictationDescription dictationDescription) {
        return new Dictation(sentence, sentenceWithBlank, wordBlank, googleTranslate.getAudioLink(sentence), translation);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentenceWithBlank() {
        return sentenceWithBlank;
    }

    public void setSentenceWithBlank(String sentenceWithBlank) {
        this.sentenceWithBlank = sentenceWithBlank;
    }

    public String getWordBlank() {
        return wordBlank;
    }

    public void setWordBlank(String wordBlank) {
        this.wordBlank = wordBlank;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "type: " + type + "\n"
                + "sentence: " + sentence + "\n"
                + "sentenceWithBlank: " + sentenceWithBlank + "\n"
                + "wordBlank: " + wordBlank + "\n"
                + "translation: " + translation;
    }
}
