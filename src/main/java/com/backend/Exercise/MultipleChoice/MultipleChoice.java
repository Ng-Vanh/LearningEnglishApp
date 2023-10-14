package com.backend.Exercise.MultipleChoice;

import com.backend.Exercise.Utils.Exercise;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.ChatGPT.ChatGPT;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import com.backend.ChatGPT.PromptLoader;

public class MultipleChoice extends Exercise {
    private String question;
    private Options options;
    private String correctAnswer;
    private String explanation;
    private final String prefix = "MultipleChoice";

    public MultipleChoice(String exerciseType) {
        super();
        String promptName = prefix + "-" + exerciseType;
        try {
            String prompt = PromptLoader.getPrompt(promptName);
            generateExercise(prompt);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void generateExercise(String query) {
        try {
//            String query = "Generate one, only one challenging English vocabulary practice exercise in JSON-like format with a sentence containing a blank in the question, four options (A, B, C, D) for completion, a specified correct answer, and a brief explanation for the definition of each of the options. For the explanation, it should be like a paragraph, no special characters. The format should follow this pattern: {question: , options: {A: , B: , C: , D: }, correctAnswer: , explanation: }.";
            String jsonString = ChatGPT.getGPTAnswer(query);
            System.out.println(jsonString);
            System.out.println("-------------------------------------\n");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode optionsNode = jsonNode.get("options");

            String question = jsonNode.get("question").asText();
            Options options = new Options();
            String correctAnswer = jsonNode.get("correctAnswer").asText();
            String explanation = jsonNode.get("explanation").asText();

            options.setOptionA(optionsNode.get("A").asText());
            options.setOptionB(optionsNode.get("B").asText());
            options.setOptionC(optionsNode.get("C").asText());
            options.setOptionD(optionsNode.get("D").asText());

            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
            this.explanation = explanation;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public MultipleChoice fromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, MultipleChoice.class);
    }

    @Override
    public boolean isCorrect(String userAnswer) {
        return userAnswer.equals(correctAnswer);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(question).append("\n");
        result.append(options).append("\n");
        result.append("Correct answer: ").append(correctAnswer).append("\n");
        result.append("Explanation: ").append("\n").append(explanation);
        return result.toString();
    }

    public static void main(String[] args) {
        MultipleChoice multipleChoice = new MultipleChoice("Blank");
        System.out.println(multipleChoice.getQuestion());
        System.out.println(multipleChoice.getOptions());
        System.out.println(multipleChoice.getCorrectAnswer());
        System.out.println(multipleChoice.getExplanation());

//        System.out.println(multipleChoice);
    }
}
