package com.example.dictionaryenvi.Exercise.ExerciseScene;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Utils.Exercise;
import com.example.dictionaryenvi.Exercise.Exercises.Dictation.Dictation_Application;
import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Application;
import com.example.dictionaryenvi.Exercise.Utils.TimerManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import javafx.scene.control.ButtonBar;
import javafx.util.Duration;

import static com.backend.Exercise.Utils.ExerciseLoader.getExerciseListFromSimpleTopicWordList;
import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.globalFullSimpleTopicWordList;

public class ExerciseScene_Controller {
    public static Label globalTimerLabel;
    public static TimerManager globalTimerManager;

    private static ArrayList<Exercise> fullExerciseList;

    private static Exercise globalCurrentExercise;
    public static boolean globalIsRunningExercise;

    public static int globalExerciseIndex;
    public static int globalExerciseListSize;
    public static int globalScore;
    public static int globalDurations;

    private static Dictation_Application dictation_application;
    private static Stage dictationStage;

    private static MultipleChoice_Application multipleChoice_application;
    private static Stage multipleChoiceStage;

    public static MultipleChoice globalCurrentMultipleChoice = MultipleChoice.getDefaultMultipleChoice();
    public static Dictation globalCurrentDictation = Dictation.getDefaultDictation();

    public static boolean globalShowingMultipleChoice = false;
    public static boolean globalShowingDictation = false;

    private static boolean startedMultipleChoice = false;
    private static boolean startedDictation = false;


    public static void saveUserScore() { // update score here
        System.out.println("Saved user score: " + globalScore);
//        updateScoreStatus(globalScore);
        // globalScore
    }

    private void init_Dictation() {
        try {
            dictation_application = new Dictation_Application();
            dictationStage = new Stage();
            dictationStage.setOnCloseRequest(e -> globalIsRunningExercise = false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init_MultipleChoice() {
        try {
            multipleChoice_application = new MultipleChoice_Application();
            multipleChoiceStage = new Stage();
            multipleChoiceStage.setOnCloseRequest(e -> globalIsRunningExercise = false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        fullExerciseList = getExerciseListFromSimpleTopicWordList(globalFullSimpleTopicWordList);
//        fullExerciseList = getExerciseListFromSimpleTopicWordList(userFullSimpleTopicWordList);

        globalDurations = 2;
        globalIsRunningExercise = true;
        globalExerciseIndex = 0;
        globalScore = 0;
        globalExerciseListSize = fullExerciseList.size();

        init_Dictation();
        init_MultipleChoice();

        Collections.shuffle(fullExerciseList);
        processNextExercise();
    }

    public static void processNextExercise() {
        if (!globalIsRunningExercise || fullExerciseList.isEmpty()) {
            globalIsRunningExercise = false;
            return;
        }

        globalCurrentExercise = fullExerciseList.remove(0);
        globalExerciseIndex += 1;
        System.out.println("Processing next exercise: " + globalCurrentExercise);

        if (globalCurrentExercise instanceof MultipleChoice) {
            globalCurrentMultipleChoice = (MultipleChoice) globalCurrentExercise;
            globalShowingMultipleChoice = true;
            globalShowingDictation = false;
            showMultipleChoiceScene();
        } else if (globalCurrentExercise instanceof Dictation) {
            globalCurrentDictation = (Dictation) globalCurrentExercise;
            globalShowingDictation = true;
            globalShowingMultipleChoice = false;
            showDictationScene();
        }
    }

    private static void showMultipleChoiceScene() {
        if (!startedMultipleChoice) {
            try {
                multipleChoice_application.start(multipleChoiceStage);
                startedMultipleChoice = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        multipleChoice_application.getController().updateQuestion();
    }

    private static void showDictationScene() {
        if (!startedDictation) {
            try {
                dictation_application.start(dictationStage);
                startedDictation = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dictation_application.getController().updateQuestion();
    }

    public static void handleScene() {
        Platform.runLater(() -> {
            System.out.println(" ============ ");
            System.out.println("Mul: " + globalShowingMultipleChoice);
            System.out.println("Dic: " + globalShowingDictation);
            System.out.println("=============");

            if (globalShowingMultipleChoice && multipleChoiceStage != null && !multipleChoiceStage.isShowing()) {
                multipleChoiceStage.show();
            } else if (globalShowingDictation && dictationStage != null && !dictationStage.isShowing()) {
                dictationStage.show();
            }

            if (!globalShowingMultipleChoice && multipleChoiceStage != null && multipleChoiceStage.isShowing()) {
                processNextExercise();
                multipleChoiceStage.hide();
            }
            if (!globalShowingDictation && dictationStage != null && dictationStage.isShowing()) {
                processNextExercise();
                dictationStage.hide();
            }
        });
    }
}
