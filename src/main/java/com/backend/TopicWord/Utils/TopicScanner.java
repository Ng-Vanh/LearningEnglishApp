package com.backend.TopicWord.Utils;

import java.io.File;
import java.util.ArrayList;

public class TopicScanner {
    private static final String folderPath = "src/main/java/com/backend/TopicWord/Topics";

    public static ArrayList<String> scanForTopics() {
        ArrayList<String> topics = new ArrayList<>();

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path: " + folderPath);
            return topics;
        }

        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    String topic = extractTopicFromFileName(file.getName());
                    if (topic != null) {
                        topics.add(topic);
                    }
                }
            }
        }
        return topics;
    }

    private static String extractTopicFromFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex);
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayList<String> topics = scanForTopics();

        System.out.println("List of Topics:");
        for (String topic : topics) {
            System.out.println(topic);
        }
    }
}