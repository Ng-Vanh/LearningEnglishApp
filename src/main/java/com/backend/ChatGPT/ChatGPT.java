package com.backend.ChatGPT;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

public class ChatGPT {
    private static final String endpoint = "https://api.openai.com/v1/chat/completions";
    private static final String apiKey = "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";
    private static final String model = "gpt-3.5-turbo";

    private static final CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String getGPTAnswer(String query) {
        try {
            URI uri = URI.create(endpoint);
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + query + "\"}]}";
            System.out.println(body);

            httpPost.setEntity(new StringEntity(body));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return parseGPTResponse(result);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    private static String parseGPTResponse(String text) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(text);

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
