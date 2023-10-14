package com.backend.OnlineDictionary.OnlineDictionaries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class FreeDictionary {
    private static final String BASE_ENDPOINT = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private ObjectMapper objectMapper;

    public FreeDictionary() {
        this.objectMapper = new ObjectMapper();
    }

    private String fetchWordData(String word) throws IOException {
        URL obj = URI.create(BASE_ENDPOINT + word).toURL();
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        return response.toString();
    }

    public String getAudioLink(String word) throws IOException {
        String jsonString = fetchWordData(word);
        try {
            return extractAudioLink(jsonString);
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to process the JSON response.", e);
        }
    }

    private String extractAudioLink(String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonString).get(0);
        return getJsonString(jsonNode, "phonetics", 0, "audio");
    }

    private String getJsonString(JsonNode parentNode, Object... path) {
        JsonNode currentNode = parentNode;
        for (Object key : path) {
            if (key instanceof String) {
                currentNode = currentNode.get((String) key);
            } else if (key instanceof Integer) {
                currentNode = currentNode.get((int) key);
            }

            if (currentNode == null) {
                return "";
            }
        }
        return currentNode.asText();
    }

    public static void main(String[] args) {
        FreeDictionary dictionary = new FreeDictionary();
        try {
            String audioLink = dictionary.getAudioLink("bomb");
            System.out.println("Audio Link: " + audioLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
