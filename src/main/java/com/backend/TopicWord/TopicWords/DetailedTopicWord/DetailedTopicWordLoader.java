package com.backend.TopicWord.TopicWords.DetailedTopicWord;

import com.backend.Exercise.Exercises.Dictation.DictationDescription;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoiceDescription;
import com.backend.Exercise.Utils.Exercise;
import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;
import com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWord;
import com.backend.TopicWord.Utils.Description;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWordLoader.getSimpleTopicWordList;
import static com.backend.TopicWord.Utils.TopicScanner.scanForTopics;

public class DetailedTopicWordLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOPIC_WORD_DIR = "src/main/java/com/backend/TopicWord/Topics/";
    private static final String SIMPLE_TOPIC_WORD_DIR = "src/main/java/com/backend/TopicWord/Words/SimpleTopicWords.txt";
    private static GoogleTranslate googleTranslate = new GoogleTranslate();
    private static StringBuilder sb = new StringBuilder();
    private static ArrayList<SimpleTopicWord> simpleTopicWordList = new ArrayList<>();
    private static HashMap<SimpleTopicWord, DetailedTopicWord> simpleTopicWordToDetailedTopicWordMap = new HashMap<>();

    public static final HashMap<String, ArrayList<DetailedTopicWord>> globalFullDetailedTopicWordMap = getDetailedTopicWordMap();
    public static final ArrayList<SimpleTopicWord> globalFullSimpleTopicWordList = getSimpleTopicWordList();

    private static void addMissingFields(DetailedTopicWord detailedTopicWord, String topic) {
        String phonetic = detailedTopicWord.getDefinition().getPhonetic();
        if (phonetic.charAt(0) != '/') {
            phonetic = '/' + phonetic + '/';
            detailedTopicWord.getDefinition().setPhonetic(phonetic);
        }

        detailedTopicWord.getDefinition().setTopic(topic);
        detailedTopicWord.getDefinition().setAudioLink(googleTranslate.getAudioLink(detailedTopicWord.getDefinition().getWord()));

        Description description = detailedTopicWord.getQuiz().getDescription();

        if ((detailedTopicWord.getQuiz().getExerciseType()).contains("Dictation")) {
            DictationDescription dictationDescription = (DictationDescription) description;
            Exercise exercise = dictationDescription.getDictation(dictationDescription);
            detailedTopicWord.getQuiz().setExercise(exercise);
        } else {
            MultipleChoiceDescription multipleChoiceDescription = (MultipleChoiceDescription) description;
            Exercise exercise = multipleChoiceDescription.getMultipleChoice(multipleChoiceDescription);
            detailedTopicWord.getQuiz().setExercise(exercise);
        }
    }

    public static ArrayList<DetailedTopicWord> getWordsFromTopic(String topicName) {
        File file = new File(TOPIC_WORD_DIR + topicName + ".txt");
        try {
            // Directly deserialize the JSON file into a container class
            DetailedTopicWordContainer container = objectMapper.readValue(file, DetailedTopicWordContainer.class);
            String topic = container.getTopic();

            HashSet<DetailedTopicWord> detailedTopicWordSet = new HashSet<>(container.getWords());
            ArrayList<DetailedTopicWord> detailedTopicWordList = new ArrayList<>(detailedTopicWordSet);
//            System.out.println(topic);

            for (DetailedTopicWord detailedTopicWord: detailedTopicWordList) {
                addMissingFields(detailedTopicWord, topicName);

                String word = detailedTopicWord.getDefinition().getWord();
                simpleTopicWordList.add(new SimpleTopicWord(topic, word));

                SimpleTopicWord simpleTopicWord = new SimpleTopicWord(topic, word);
                simpleTopicWordToDetailedTopicWordMap.put(simpleTopicWord, detailedTopicWord);

//                System.out.println(detailedTopicWord.getDefinition());
//                System.out.println("---------------------------------");
//                System.out.println(detailedTopicWord.getQuiz().getExercise());
////
//                System.out.println();
            }

            return detailedTopicWordList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static HashMap<String, ArrayList<DetailedTopicWord>> getDetailedTopicWordMap() {
        ArrayList<String> topics = scanForTopics();
        HashMap<String, ArrayList<DetailedTopicWord>> detailedTopicWordMap = new HashMap<>();

        simpleTopicWordList.clear();
        simpleTopicWordToDetailedTopicWordMap.clear();

        for (String topic : topics) {
            ArrayList<DetailedTopicWord> detailedTopicWordList = getWordsFromTopic(topic);
            detailedTopicWordMap.put(topic, detailedTopicWordList);
        }

        saveToSimpleTopicWordFile();

        return detailedTopicWordMap;
    }

    private static void saveToSimpleTopicWordFile() {
        sb.setLength(0);
        sb.append("{\n" + "\"words\": [\n");

        int simpleTopicWordListSize = simpleTopicWordList.size();
        for (int i = 0; i < simpleTopicWordListSize; ++i) {
            SimpleTopicWord simpleTopicWord = simpleTopicWordList.get(i);
            sb.append("{\"topic\": \"").append(simpleTopicWord.getTopic()).append("\", \"word\": \"").append(simpleTopicWord.getWord()).append("\"}");

            if (i < simpleTopicWordListSize - 1) {
                sb.append(",");
            }

            sb.append(System.lineSeparator());
        }

        sb.append("]\n" + "}");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SIMPLE_TOPIC_WORD_DIR))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<DetailedTopicWord> getDetailedTopicWordListFromSimpleTopicWordList(ArrayList<SimpleTopicWord> simpleTopicWordList) {
        ArrayList<DetailedTopicWord> detailedTopicWordList = new ArrayList<>();
        for (SimpleTopicWord simpleTopicWord: simpleTopicWordList) {
            detailedTopicWordList.add(simpleTopicWordToDetailedTopicWordMap.get(simpleTopicWord));
        }
        return detailedTopicWordList;
    }

    public static void main(String[] args) {
//        ArrayList<DetailedTopicWord> detailedTopicWordList = getWordsFromTopic("Animal");
//        ArrayList<SimpleTopicWord> simpleTopicWordList = getSimpleTopicWordList();
//        for (TopicWord word : wordSet) {
//            System.out.println(word);
//            System.out.println();
//        }
//        System.out.println("Total words: " + wordSet.size());

//        int cnt = 0;
//        StringBuilder sb = new StringBuilder();
//        HashMap<String, ArrayList<DetailedTopicWord>> detailedTopicWordMap = getDetailedTopicWordMap();
//
//        HashMap<String, Integer> topicCntMap = new HashMap<>();
//        for (String topic: detailedTopicWordMap.keySet()) {
//            System.out.println("Topic: " + topic);
//            sb.append(topic).append(", ");
//            ArrayList<DetailedTopicWord> detailedTopicWordList = detailedTopicWordMap.get(topic);
//            for (DetailedTopicWord detailedTopicWord: detailedTopicWordList) {
//                System.out.println(detailedTopicWord);
//                System.out.println();
//                cnt += 1;
//            }
//            topicCntMap.put(topic, detailedTopicWordList.size());
//            System.out.println("===============================");
//        }
//
//        System.out.println("Total words: " + cnt);
//        System.out.println("Number of topics: " + topicCntMap.keySet().size());
//        System.out.println(sb.toString() + "\n");
//        for (String topic: topicCntMap.keySet()) {
//            System.out.println("Topic: " + topic + "\nCnt: " + topicCntMap.get(topic) + "\n");
//        }

        for (SimpleTopicWord simpleTopicWord: globalFullSimpleTopicWordList) {
            System.out.println(simpleTopicWord);
            System.out.println();
        }
        System.out.println();
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        HashMap<String, Integer> topicCntMap = new HashMap<>();
        for (String topic: globalFullDetailedTopicWordMap.keySet()) {
            System.out.println("Topic: " + topic);
            sb.append(topic).append(", ");
            ArrayList<DetailedTopicWord> detailedTopicWordList = globalFullDetailedTopicWordMap.get(topic);
            for (DetailedTopicWord detailedTopicWord: detailedTopicWordList) {
                System.out.println(detailedTopicWord);
                System.out.println();
                cnt += 1;
            }
            topicCntMap.put(topic, detailedTopicWordList.size());
            System.out.println("===============================");
        }

        System.out.println("Total words: " + cnt);
        System.out.println("Number of topics: " + topicCntMap.keySet().size());
        System.out.println(sb.toString() + "\n");
        for (String topic: topicCntMap.keySet()) {
            System.out.println("Topic: " + topic + "\nCnt: " + topicCntMap.get(topic) + "\n");
        }
    }
}
