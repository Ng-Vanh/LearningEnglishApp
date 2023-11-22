package com.backend.TopicWord.TopicWords.SimpleTopicWord;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class SimpleTopicWordLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String FILE_DIR = "src/main/java/com/backend/TopicWord/Words/SimpleTopicWords.txt";

    public static ArrayList<SimpleTopicWord> getSimpleTopicWordList() {
        File file = new File(FILE_DIR);
        try {
            SimpleTopicWordContainer container = objectMapper.readValue(file, SimpleTopicWordContainer.class);

            HashSet<SimpleTopicWord> simpleTopicWordSet = new HashSet<>(container.getWords());
            ArrayList<SimpleTopicWord> simpleTopicWordList = new ArrayList<>(simpleTopicWordSet);

            return simpleTopicWordList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ArrayList<SimpleTopicWord> simpleTopicWordList = getSimpleTopicWordList();
//        for (TopicWord word : wordSet) {
//            System.out.println(word);
//            System.out.println();
//        }
//        System.out.println("Total words: " + wordSet.size());
    }
}
