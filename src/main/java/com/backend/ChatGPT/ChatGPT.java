package com.backend.ChatGPT;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Create package.
 * modify pom.xml.
 * modify module-info.java * to make the project require jackson databind.
 */

public class ChatGPT {
    private static String endpoint = "https://api.openai.com/v1/chat/completions";
    private static String apiKey = "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";
    private static String model = "gpt-3.5-turbo";

//    public ChatGPT() {
//        endpoint = "https://api.openai.com/v1/chat/completions";
//        apiKey = "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";
//        model = "gpt-3.5-turbo";
//    }

    public static String getGPTAnswer(String query) {
        try {
            URL obj = URI.create(endpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + query + "\"}]}";
            System.out.println(body);

            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            if (connection.getResponseCode() == 200) {
                // Request was successful, parse and return the response.
                String responseBody = response.toString();
//                System.out.println("Response: " + responseBody);
                return parseGPTResponse(responseBody);
            } else {

                System.err.println("Error response code: " + connection.getResponseCode());
            }

        } catch (IOException e) {
            // Handle IO errors here.
            System.err.println("IO error: " + e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e);
        }
        return "";
    }

    private static String parseGPTResponse(String text) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(text);

            // Get the "content" field from the JSON
            String content = rootNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
