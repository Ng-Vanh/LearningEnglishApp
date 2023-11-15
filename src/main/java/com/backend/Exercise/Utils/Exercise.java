package com.backend.Exercise.Utils;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Exercises.MultipleChoice.Options;
import com.backend.OnlineDictionary.Utils.AudioTranslation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Exercise {

    public Exercise() {

    }

    protected abstract void generateExercise(String promptName);

    public abstract boolean isCorrect(String userAnswer);

    public abstract String getQuestion();

    public abstract String getCorrectAnswer();

    protected static List<String> readJsonFile(String filePath) {
        List<String> jsonStringList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    // Append non-empty lines to the StringBuilder
                    jsonStringBuilder.append(line);
                } else {
                    // If an empty line is encountered, add the JSON object to the list
                    if (!jsonStringBuilder.isEmpty()) {
                        jsonStringList.add(jsonStringBuilder.toString());
                        jsonStringBuilder.setLength(0);  // Clear the StringBuilder for the next JSON object
                    }
                }
            }

            // Check for any remaining JSON object after the last empty line
            if (!jsonStringBuilder.isEmpty()) {
                jsonStringList.add(jsonStringBuilder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return jsonStringList;
    }

    @Override
    public abstract String toString();

    public static void main(String[] args) {
        Exercise E_multipleChoice = new MultipleChoice("Blank"); // Blank, Synonyms , Antonyms
        MultipleChoice multipleChoice = (MultipleChoice) E_multipleChoice;
//        MultipleChoice multipleChoice = new MultipleChoice("Blank");

        System.out.println(multipleChoice.getQuestion());
        System.out.println(multipleChoice.getOptions());
        System.out.println(multipleChoice.getCorrectAnswer());
        System.out.println(multipleChoice.getExplanation());
        System.out.println(multipleChoice.isCorrect("word"));
//        System.out.println(multipleChoice);

        System.out.println("-------------------------------------------------");

        Exercise E_dictation = new Dictation("Blank");
        Dictation dictation = (Dictation) E_dictation;
//        Dictation dictation = new Dictation("Blank");

        System.out.println(dictation.getSentenceWithBlank());
        System.out.println(dictation.getWordBlank());
        System.out.println(dictation.getAudioTranslation().getAudioLink());
        System.out.println(dictation.isCorrect("word"));
//        System.out.println(dictation);
    }
}
