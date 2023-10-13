package com.backend.OnlineDictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class FreeDictionary {
    private final String endpoint = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    public static void main(String[] args) {
        String query = "bomb";
        String endpoint = "https://api.dictionaryapi.dev/api/v2/entries/en/" + query;
        try {
            URL obj = URI.create(endpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            // Response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            String jsonString = response.toString();
            System.out.println(jsonString);
            System.out.println("================");
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonString).get(0);

                String word = jsonNode.get("word").asText();
                String phonetics = jsonNode.get("phonetics").get(0).get("text").asText(); ///
                String audio_link = jsonNode.get("phonetics").get(0).get("audio").asText();
                System.out.println(word);
                System.out.println(phonetics);
                System.out.println(audio_link);

                JsonNode meaningsNode = jsonNode.get("meanings");

                for (JsonNode meaning : meaningsNode) {
                    String partOfSpeech = meaning.get("partOfSpeech").asText();
                    JsonNode definitions = meaning.get("definitions");

                    System.out.println(partOfSpeech);
                    for (JsonNode definition : definitions) {
                        String thisDefinition = definition.get("definition") != null ? definition.get("definition").asText() : "";
                        String thisExample = definition.get("example") != null ? definition.get("example").asText() : "";

                        System.out.println("   " + "definition: " + thisDefinition);
                        System.out.println("   " + "example: " + thisExample);
                        System.out.println("   " + "-----------------------------------------");
                    }
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e);
        }


    }
}
