package com.example.dictionaryenvi.Exercise.ExerciseScene;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Utils.Exercise;
import com.example.dictionaryenvi.Exercise.Exercises.Dictation.Dictation_Application;
import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Application;
import com.example.dictionaryenvi.Exercise.Utils.TimerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

import static com.backend.Exercise.Utils.ExerciseLoader.getExerciseListFromSimpleTopicWordList;
import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.globalFullSimpleTopicWordList;

public class ExerciseScene_Controller {
    public static Label globalTimerLabel;
    public static TimerManager globalTimerManager;

    private ArrayList<Exercise> fullExerciseList;

    public static Exercise globalCurrentExercise;
    public static boolean globalIsRunningExercise;

    public static int globalExerciseIndex;
    public static int globalExerciseListSize;
    public static int globalScore;
    public static int globalDurations;

    public static void saveUserScore() {
        System.out.println("Saved user score: " + globalScore);
        // globalScore
    }

    @FXML
    public void initialize() {
        fullExerciseList = getExerciseListFromSimpleTopicWordList(globalFullSimpleTopicWordList);
//        fullExerciseList = getExerciseListFromSimpleTopicWordList(userFullSimpleTopicWordList);

        globalDurations = 60;
        globalIsRunningExercise = true;
        globalExerciseIndex = 0;
        globalScore = 0;
        globalExerciseListSize = fullExerciseList.size();

        Collections.shuffle(fullExerciseList);
        processNextExercise();
    }

    public void processNextExercise() {
        if (!globalIsRunningExercise || fullExerciseList.isEmpty()) {
            globalIsRunningExercise = false;
            return;
        }

        globalCurrentExercise = fullExerciseList.remove(0);
        globalExerciseIndex += 1;
        System.out.println(globalCurrentExercise);
        if (globalCurrentExercise instanceof MultipleChoice) {
            showMultipleChoiceScene();
        } else if (globalCurrentExercise instanceof Dictation) {
            showDictationScene();
        }
    }

    private void showMultipleChoiceScene() {
        try {
            MultipleChoice_Application multipleChoiceApp = new MultipleChoice_Application();
            Stage stage = new Stage();
            multipleChoiceApp.start(stage);
            stage.setOnHidden(e -> processNextExercise());
            stage.setOnCloseRequest(e -> globalIsRunningExercise = false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDictationScene() {
        try {
            Dictation_Application dictationApp = new Dictation_Application();
            Stage stage = new Stage();
            dictationApp.start(stage);
            stage.setOnHidden(e -> processNextExercise());
            stage.setOnCloseRequest(e -> globalIsRunningExercise = false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}