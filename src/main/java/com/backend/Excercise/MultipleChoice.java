package com.backend.Excercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.ChatGPT.ChatGPT;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MultipleChoice {
    private String question;
    private Options options;
    private String correctAnswer;

    public static class Options {
        @JsonProperty("A")
        private String optionA;

        @JsonProperty("B")
        private String optionB;

        @JsonProperty("C")
        private String optionC;

        @JsonProperty("D")
        private String optionD;

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public String getOptionD() {
            return optionD;
        }
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public MultipleChoice fromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, MultipleChoice.class);
    }

    public MultipleChoice() {
        try {
            String query = "Generate a challenging English vocabulary practice exercise in JSON-like format with a sentence containing a blank in the question, four options (A, B, C, D) for completion, and a specified correct answer. The format should follow this pattern: {question: , options: {A: , B: , C: , D: }, correctAnswer: }.";
            String jsonString = ChatGPT.getGPTAnswer(query);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode optionsNode = jsonNode.get("options");

            String question = jsonNode.get("question").asText();
            Options options = new Options();
            String correctAnswer = jsonNode.get("correctAnswer").asText();

            options.setOptionA(optionsNode.get("A").asText());
            options.setOptionB(optionsNode.get("B").asText());
            options.setOptionC(optionsNode.get("C").asText());
            options.setOptionD(optionsNode.get("D").asText());

            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println(question);
        System.out.println("A: " + options.getOptionA());
        System.out.println("B: " + options.getOptionB());
        System.out.println("C: " + options.getOptionC());
        System.out.println("D: " + options.getOptionD());
        System.out.println("Correct answer: " + correctAnswer);
    }
}
