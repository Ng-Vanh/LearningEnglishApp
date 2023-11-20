package com.backend.Exercise.Exercises.Dictation;

import com.backend.ChatGPT.ChatGPT;
import com.backend.ChatGPT.PromptLoader;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Exercises.MultipleChoice.Options;
import com.backend.Exercise.Utils.Exercise;
import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;
import com.backend.OnlineDictionary.Utils.AudioTranslation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Dictation extends Exercise {
    private String sentence;
    private String sentenceWithBlank;
    private String wordBlank;
    private AudioTranslation audioTranslation;
    private static final String prefix = "Dictation";

    private static final HashSet<String> typeSet = new HashSet<>(Set.of("Blank"));

    private static final String bankFolderPath = "src/main/java/com/backend/Exercise/ExerciseBank/Dictation/";

//    private String type = "Dictation";
//    private String exerciseType;
    private DictationDescription description;

//    public String getExerciseType() {
//        return exerciseType;
//    }
//
//    public void setExerciseType(String exerciseType) {
//        this.exerciseType = exerciseType;
//    }

    public DictationDescription getDescription() {
        return description;
    }

    public void setDescription(DictationDescription description) {
        this.description = description;
    }

    public static Dictation getDefaultDictation() {
        return new Dictation("Sentence", "sentenceWithBlank", "wordBlank", "audioLink", "translation");
    }

//    public static Dictation getDefaultDictation() {
//        return new Dictation("Sentence", "aasdjfhlaksdhflaskdfhljhlhlshlksadfsdfhsadflkjasdflkjasdflkjhasdljkfhahlhlkjasdfsdhlkajsdfhlaksdjfhlsadlkjfhslkjhlkjlkjsdasdfasdf", "wordBlank", "audioLink", "translation");
//    }

    public Dictation() {

    }

    void assign(Dictation dictation) {
        this.sentence = dictation.getSentence();
        this.sentenceWithBlank = dictation.getSentenceWithBlank();
        this.wordBlank = dictation.getWordBlank();
        this.audioTranslation = dictation.audioTranslation;
    }

    public Dictation(String sentence, String sentenceWithBlank, String wordBlank) {
        this.sentence = sentence;
        this.sentenceWithBlank = sentenceWithBlank;
        this.wordBlank = wordBlank;
        this.audioTranslation = new GoogleTranslate(sentence);
    }

    public Dictation(String sentence, String sentenceWithBlank, String wordBlank, String audioLink, String translation) {
        this.sentence = sentence;
        this.sentenceWithBlank = sentenceWithBlank;
        this.wordBlank = wordBlank;
        this.audioTranslation = new GoogleTranslate(audioLink, translation);
    }

    public Dictation(String exerciseType) {
        super();
        String promptName = prefix + "-" + exerciseType;
        try {
            String prompt = PromptLoader.getPrompt(promptName);
            generateExercise(prompt);
//            this.exerciseType = prefix + "-" + exerciseType;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public Dictation(String exerciseType, String jsonString) {
        if (!exerciseType.contains(prefix)) {
            throw new IllegalArgumentException("Not a dictation");
        }
        else {
            Dictation dictation = loadFromJson(jsonString);
            assign(dictation);
//            this.exerciseType = prefix + "-" + exerciseType;
        }
    }

    private static Dictation loadFromJson(String jsonString) {
        try {
//            ChatGPT chatGPT = new ChatGPT();
//            String jsonString = chatGPT.getGPTAnswer(query);


//            System.out.println(jsonString);
//            System.out.println("-------------------------------------\n");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String sentence = jsonNode.get("sentence").asText();
            String sentenceWithBlank = jsonNode.get("sentenceWithBlank").asText();
            String wordBlank = jsonNode.get("wordBlank").asText();

            if (jsonNode.has("audioLink") && jsonNode.has("translation")) {
                String audioLink = jsonNode.get("audioLink").asText();
                String translation = jsonNode.get("translation").asText();

                return new Dictation(sentence, sentenceWithBlank, wordBlank, audioLink, translation);
            } else {
                return new Dictation(sentence, sentenceWithBlank, wordBlank);
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Dictation();
    }

    public static ArrayList<Dictation> loadFromBank(String exerciseType) {
        if (typeSet.contains(exerciseType)) {
            String filename = prefix + "-" + exerciseType + ".txt";
            String filepath = bankFolderPath + filename;

            System.out.println(filename);
            System.out.println(filepath);

            ArrayList<String> jsonList = new ArrayList<>(readJsonFile(filepath));

//            ArrayList<String> jsonList = new ArrayList<>(readJsonFile(filepath.toString()));

            ArrayList<Dictation> dictationList = new ArrayList<>();

            for (String json : jsonList) {
                dictationList.add(loadFromJson(json));
            }

            HashSet<Dictation> dictationSet = new HashSet<>(dictationList);

            dictationList = new ArrayList<>(dictationSet);

            return dictationList;

        } else {
            throw new IllegalArgumentException("Invalid exerciseType: " + exerciseType);
        }
    }

    public static ArrayList<Dictation> loadFromBank() {
        ArrayList<Dictation> dictationList = new ArrayList<>();
        for (String type : typeSet) {
            dictationList.addAll(loadFromBank(type));
        }
        return dictationList;
    }


    @Override
    protected void generateExercise(String query) {
        String jsonString = ChatGPT.getGPTAnswer(query);

        Dictation dictation = loadFromJson(jsonString);
        assign(dictation);
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

    @Override
    public String getQuestion() {
        return getSentenceWithBlank();
    }

    public void setSentenceWithBlank(String sentenceWithBlank) {
        this.sentenceWithBlank = sentenceWithBlank;
    }

    public String getWordBlank() {
        return wordBlank;
    }

    @Override
    public String getCorrectAnswer() {
        return getWordBlank();
    }

    public void setWordBlank(String wordBlank) {
        this.wordBlank = wordBlank;
    }

    @Override
    public boolean isCorrect(String userAnswer) {
        return userAnswer.equalsIgnoreCase(wordBlank);
    }

    public AudioTranslation getAudioTranslation() {
        return audioTranslation;
    }

    public void setAudioTranslation(AudioTranslation audioTranslation) {
        this.audioTranslation = audioTranslation;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Sentence: ").append(sentence).append("\n");
        result.append("Sentence with blank: ").append(sentenceWithBlank).append("\n");
        result.append("Word blank: ").append(wordBlank).append("\n");
        result.append(audioTranslation.toString());
        return result.toString();
    }

    public static void main(String[] args) {
//        Dictation dictation = new Dictation("Blank");
//        System.out.println(dictation.getSentenceWithBlank());
//        System.out.println(dictation.getWordBlank());
//        System.out.println(dictation.getAudioTranslation().getAudioLink());
//        System.out.println(dictation.isCorrect("word"));

//        Dictation customDictation = new Dictation("Hello, how are you", "Hello, how ___ you", "are");

//        System.out.println(dictation);

//        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/java/com/backend/Exercise/ExerciseBank/Dictation/Dictation-Blank-GPT.txt"))) {
//            // Construct the JSON object as a string
//            ArrayList<Dictation> dictationList = new ArrayList<>(loadFromBank());
//            for (Dictation dictation: dictationList) {
//                String sentence = dictation.getSentence();
//                String sentenceWithBlank = dictation.getSentenceWithBlank();
//                String wordBlank = dictation.getWordBlank();
//                AudioTranslation audioTranslation = dictation.getAudioTranslation();
//                String audioLink = audioTranslation.getAudioLink();
//                String translation = audioTranslation.getTranslation();
//
//                String jsonObject = "{\n" +
//                        "\"sentence\": \"" + sentence + "\",\n" +
//                        "\"sentenceWithBlank\": \"" + sentenceWithBlank + "\",\n" +
//                        "\"wordBlank\": \"" + wordBlank + "\",\n" +
//                        "\"audioLink\" : \"" + audioLink + "\",\n" +
//                        "\"translation\": \"" + translation + "\"\n" +
//                        "}";
//
//                writer.println(jsonObject);
//                writer.println("");
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ArrayList<Dictation> dictationList = new ArrayList<>(loadFromBank());
        System.out.println(dictationList.get(0));
    }
}

