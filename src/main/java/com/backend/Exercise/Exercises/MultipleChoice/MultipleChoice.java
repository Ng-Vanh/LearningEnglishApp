package com.backend.Exercise.Exercises.MultipleChoice;

import com.backend.Exercise.Utils.Exercise;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.ChatGPT.ChatGPT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.backend.ChatGPT.PromptLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MultipleChoice extends Exercise {
    private String question;
    private Options options;
    private String correctAnswer;
    private String explanation;
    private static final String prefix = "MultipleChoice";

    private static final HashSet<String> typeSet = new HashSet<>(Set.of("Antonyms", "Blank", "Synonyms"));

    private static final String bankFolderPath = "src/main/java/com/backend/Exercise/ExerciseBank/MultipleChoice/";

    public MultipleChoice() {

    }

    void assign(MultipleChoice multipleChoice) {
        this.question = multipleChoice.getQuestion();
        this.options = multipleChoice.getOptions();
        this.correctAnswer = multipleChoice.getCorrectAnswer();
        this.explanation = multipleChoice.getExplanation();
    }

    public MultipleChoice(String question, Options options, String correctAnswer, String explanation) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

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

    private static String formatExplanation(String explanation) {
//         Split the input string using "A:", "B:", "C:", and "D:" as delimiters
        String[] parts = explanation.split("(A:|B:|C:|D:)");

        StringBuilder result = new StringBuilder();

        // Format each part and append to the result
        for (String part : parts) {
            // Skip empty and whitespace-only parts
            if (!part.trim().isEmpty()) {
                result.append(part.trim()).append("\n\n");
            }
        }

        return result.toString();
    }

    private static MultipleChoice loadFromJson(String jsonString) {
        try {
//            System.out.println(jsonString);
//            System.out.println("-------------------------------------\n");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode optionsNode = jsonNode.get("options");

            String question = jsonNode.get("question").asText();
            Options options = new Options();
            String correctAnswer = jsonNode.get("correctAnswer").asText();
            String explanation = formatExplanation(jsonNode.get("explanation").asText());

            options.setOptionA(optionsNode.get("A").asText());
            options.setOptionB(optionsNode.get("B").asText());
            options.setOptionC(optionsNode.get("C").asText());
            options.setOptionD(optionsNode.get("D").asText());

            return new MultipleChoice(question, options, correctAnswer, explanation);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new MultipleChoice();
    }


    @Override
    protected void generateExercise(String query) {

//            String query = "Generate one, only one challenging English vocabulary practice exercise in JSON-like format with a sentence containing a blank in the question, four options (A, B, C, D) for completion, a specified correct answer, and a brief explanation for the definition of each of the options. For the explanation, it should be like a paragraph, no special characters. The format should follow this pattern: {question: , options: {A: , B: , C: , D: }, correctAnswer: , explanation: }.";
//            ChatGPT chatGPT = new ChatGPT();
//            String jsonString = chatGPT.getGPTAnswer(query);

        String jsonString = ChatGPT.getGPTAnswer(query);
        MultipleChoice multipleChoice = loadFromJson(jsonString);

        assign(multipleChoice);
    }

    public static ArrayList<MultipleChoice> loadFromBank(String exerciseType) {
        if (typeSet.contains(exerciseType)) {
            String filename = prefix + "-" + exerciseType + ".txt";
            String filepath = bankFolderPath + filename;

            System.out.println(filename);
            System.out.println(filepath);

            ArrayList<String> jsonList = new ArrayList<>(readJsonFile(filepath));

//            ArrayList<String> jsonList = new ArrayList<>(readJsonFile(filepath.toString()));

            ArrayList<MultipleChoice> multipleChoiceList = new ArrayList<>();

            for (String json : jsonList) {
                multipleChoiceList.add(loadFromJson(json));
            }

            HashSet<MultipleChoice> multipleChoiceSet = new HashSet<>(multipleChoiceList);

            multipleChoiceList = new ArrayList<>(multipleChoiceSet);

            return multipleChoiceList;

        } else {
            throw new IllegalArgumentException("Invalid exerciseType: " + exerciseType);
        }
    }

    public static ArrayList<MultipleChoice> loadFromBank() {
        ArrayList<MultipleChoice> multipleChoiceList = new ArrayList<>();
        for (String type : typeSet) {
            multipleChoiceList.addAll(loadFromBank(type));
        }
        return multipleChoiceList;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Options getOptions() {
        return options;
    }

    public String getOptionA() {
        return options.getOptionA();
    }

    public String getOptionB() {
        return options.getOptionB();
    }

    public String getOptionC() {
        return options.getOptionC();
    }

    public String getOptionD() {
        return options.getOptionD();
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
//        MultipleChoice multipleChoice = new MultipleChoice("Blank");
//        System.out.println(multipleChoice.getQuestion());
//        System.out.println(multipleChoice.getOptions());
//        System.out.println(multipleChoice.getCorrectAnswer());
//        System.out.println(multipleChoice.getExplanation());
//        System.out.println(multipleChoice.isCorrect("word"));

        ArrayList<MultipleChoice> multipleChoiceList = loadFromBank();
        System.out.println(multipleChoiceList.get(0));

//        MultipleChoice customMultipleChoice = new MultipleChoice(
//                "Question",
//                new Options("opA", "opB", "opC", "opD"),
//                "correctAnswer", "explanation");

//        System.out.println(multipleChoice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultipleChoice that)) return false;
        return Objects.equals(question, that.question) && Objects.equals(options, that.options) && Objects.equals(correctAnswer, that.correctAnswer) && Objects.equals(explanation, that.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, options, correctAnswer, explanation);
    }
}
