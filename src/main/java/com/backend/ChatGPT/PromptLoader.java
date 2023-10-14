package com.backend.ChatGPT;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PromptLoader {
    private static final String promptsPath = "src/main/java/com/backend/ChatGPT/prompts.txt";
    private static Map<String, String> promptsMap = null;

    static {
        try {
            loadPrompts();
        } catch (IOException e) {
            System.err.println("Failed to load prompts from file.");
        }
    }

    private static void loadPrompts() throws IOException {
        if (promptsMap != null) {
            System.out.println("promptsMap already loaded.");
            return;
        }
        promptsMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(promptsPath))) {
            String line;
            String currentPromptKey = null;
            StringBuilder currentPromptContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (currentPromptKey == null) {
                        currentPromptKey = line;
                    } else {
                        currentPromptContent.append(line).append(" ");
                    }
                } else {
                    if (currentPromptKey != null) {
                        promptsMap.put(currentPromptKey, currentPromptContent.toString().trim());
                        currentPromptKey = null;
                        currentPromptContent = new StringBuilder();
                    }
                }
            }

            if (currentPromptKey != null) {
                promptsMap.put(currentPromptKey, currentPromptContent.toString().trim());
            }

            System.out.println("promptsMap loaded successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPrompt(String promptName) throws IllegalArgumentException {
        if (!promptsMap.containsKey(promptName)) {
            throw new IllegalArgumentException("Prompt with name: '" + promptName + "' not found.");
        }
        return promptsMap.get(promptName);
    }


    public static void main(String[] args) {
        System.out.println(PromptLoader.getPrompt("MultipleChoice-Antonym"));
    }
}
