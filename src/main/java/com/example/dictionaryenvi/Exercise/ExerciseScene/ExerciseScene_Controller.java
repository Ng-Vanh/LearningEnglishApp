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
    public static Exercise currentExercise;
    public static boolean isRunningExercise;

    public static int globalExerciseIndex;
    public static int globalExerciseListSize;
    public static int globalScore;

    @FXML
    public void initialize() {
        fullExerciseList = getExerciseListFromSimpleTopicWordList(globalFullSimpleTopicWordList);
        Collections.shuffle(fullExerciseList);
        isRunningExercise = true;
        globalExerciseIndex = 0;
        globalScore = 0;
        globalExerciseListSize = fullExerciseList.size();
        processNextExercise();
    }

    public void processNextExercise() {
        if (!isRunningExercise) {
            return;
        }
        if (!fullExerciseList.isEmpty()) {
            currentExercise = fullExerciseList.remove(0);
            globalExerciseIndex += 1;
            System.out.println(currentExercise);
            if (currentExercise instanceof MultipleChoice) {
                showMultipleChoiceScene();
            } else if (currentExercise instanceof Dictation) {
                showDictationScene();
            }
        } else {
            // All exercises are finished
        }
    }

    private void showMultipleChoiceScene() {
        try {
            MultipleChoice_Application multipleChoiceApp = new MultipleChoice_Application();
            Stage stage = new Stage();
            multipleChoiceApp.start(stage);
            stage.setOnHidden(e -> processNextExercise());
            stage.setOnCloseRequest(e -> {
                isRunningExercise = false;
            });
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
            stage.setOnCloseRequest(e -> {
                isRunningExercise = false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}