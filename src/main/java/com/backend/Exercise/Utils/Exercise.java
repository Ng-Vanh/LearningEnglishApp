package com.backend.Exercise.Utils;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Exercises.MultipleChoice.Options;
import com.backend.OnlineDictionary.Utils.AudioTranslation;

public abstract class Exercise {

    public Exercise() {

    }

    protected abstract void generateExercise(String promptName);

    public abstract boolean isCorrect(String userAnswer);

    public String getQuestion() {
        return null;
    }

    public Options getOptions() {
        return null;
    }

    public String getCorrectAnswer() {
        return null;
    }

    public String getExplanation() {
        return null;
    }

    public String getSentenceWithBlank() {
        return null;
    }

    public String getWordBlank() {
        return null;
    }

    public AudioTranslation getAudioTranslation() {
        return null;
    }

    @Override
    public abstract String toString();

    public static void main(String[] args) {
        Exercise multipleChoice = new MultipleChoice("Blank"); // Synonyms , Antonyms
        System.out.println(multipleChoice.getQuestion());
        System.out.println(multipleChoice.getOptions());
        System.out.println(multipleChoice.getCorrectAnswer());
        System.out.println(multipleChoice.getExplanation());
        System.out.println(multipleChoice.isCorrect("word"));
//        System.out.println(multipleChoice);

        System.out.println("-------------------------------------------------");

        Exercise dictation = new Dictation("Blank");
        System.out.println(dictation.getSentenceWithBlank());
        System.out.println(dictation.getWordBlank());
        System.out.println(dictation.getAudioTranslation().getAudioLink());
        System.out.println(dictation.isCorrect("word"));
//        System.out.println(dictation);
    }
}
