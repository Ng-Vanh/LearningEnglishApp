package com.backend.Exercise.Exercises.Dictation;

import com.backend.ChatGPT.ChatGPT;
import com.backend.ChatGPT.PromptLoader;
import com.backend.Exercise.Utils.Exercise;
import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;
import com.backend.OnlineDictionary.Utils.AudioTranslation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Dictation extends Exercise {
    private String sentence;
    private String sentenceWithBlank;
    private String wordBlank;
    private AudioTranslation audioTranslation;
    private final String prefix = "Dictation";

    public Dictation(String sentence, String sentenceWithBlank, String wordBlank) {
        this.sentence = sentence;
        this.sentenceWithBlank = sentenceWithBlank;
        this.wordBlank = wordBlank;
        this.audioTranslation = new GoogleTranslate(sentence);
    }

    public Dictation(String exerciseType) {
        super();
        String promptName = prefix + "-" + exerciseType;
        try {
            String prompt = PromptLoader.getPrompt(promptName);
            generateExercise(prompt);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void generateExercise(String query) {
        try {
            String jsonString = ChatGPT.getGPTAnswer(query);
            System.out.println(jsonString);
            System.out.println("-------------------------------------\n");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String sentence = jsonNode.get("sentence").asText();
            String sentenceWithBlank = jsonNode.get("sentenceWithBlank").asText();
            String wordBlank = jsonNode.get("wordBlank").asText();

            this.sentence = sentence;
            this.sentenceWithBlank = sentenceWithBlank;
            this.wordBlank = wordBlank;
            this.audioTranslation = new GoogleTranslate(sentence);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public String getSentenceWithBlank() {
        return sentenceWithBlank;
    }

    public void setSentenceWithBlank(String sentenceWithBlank) {
        this.sentenceWithBlank = sentenceWithBlank;
    }

    @Override
    public String getWordBlank() {
        return wordBlank;
    }

    public void setWordBlank(String wordBlank) {
        this.wordBlank = wordBlank;
    }

    @Override
    public boolean isCorrect(String userAnswer) {
        return userAnswer.equals(wordBlank);
    }

    @Override
    public AudioTranslation getAudioTranslation() {
        return audioTranslation;
    }

    public void setAudioTranslation(AudioTranslation audioTranslation) {
        this.audioTranslation = audioTranslation;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Sentence with blank: ").append(sentenceWithBlank).append("\n");
        result.append("Word blank: ").append(wordBlank).append("\n");
        result.append(audioTranslation.toString());
        return result.toString();
    }

    public static void main(String[] args) {
        Dictation dictation = new Dictation("Blank");
        System.out.println(dictation.getSentenceWithBlank());
        System.out.println(dictation.getWordBlank());
        System.out.println(dictation.getAudioTranslation().getAudioLink());
        System.out.println(dictation.isCorrect("word"));

//        Dictation customDictation = new Dictation("Hello, how are you", "Hello, how ___ you", "are");

//        System.out.println(dictation);
    }
}
