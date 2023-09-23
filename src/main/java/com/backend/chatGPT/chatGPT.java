package com.backend.chatGPT;

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

public class chatGPT {
    private String endpoint;
    private String apiKey;
    private String model;

    public chatGPT() {
        endpoint = "https://api.openai.com/v1/chat/completions";
        apiKey = "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";
        model = "gpt-3.5-turbo";
    }

    public String getAnswer(String query) {
        try {
            URL obj = URI.create(endpoint).toURL();
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + query + "\"}]}";
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
                return getGPTresponse(responseBody);
            } else {
                // Handle non-200 response codes here.
                System.err.println("Error response code: " + connection.getResponseCode());
                // You can add more detailed error handling logic here if needed.
            }

        } catch (IOException e) {
            // Handle IO errors here.
            System.err.println("IO error: " + e.getMessage());
            e.printStackTrace();

            throw new RuntimeException(e);
        }
        return "";
    }

    public static String getGPTresponse(String text) {
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
