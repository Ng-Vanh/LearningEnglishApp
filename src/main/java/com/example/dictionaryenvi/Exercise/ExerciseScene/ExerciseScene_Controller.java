package com.example.dictionaryenvi.Exercise.ExerciseScene;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Utils.Exercise;
import com.example.dictionaryenvi.Exercise.Exercises.Dictation.Dictation_Application;
import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Application;
import com.example.dictionaryenvi.Exercise.Utils.TimerManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    public static boolean showingMultipleChoice = false;
    public static boolean showingDictation = false;

    private static boolean startedMultipleChoice = false;
    private static boolean startedDictation = false;

    public static void saveUserScore() {
        System.out.println("Saved user score: " + globalScore);
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

        globalDurations = 60;
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
            showingMultipleChoice = true;
            showingDictation = false;
            showMultipleChoiceScene();
        } else if (globalCurrentExercise instanceof Dictation) {
            globalCurrentDictation = (Dictation) globalCurrentExercise;
            showingDictation = true;
            showingMultipleChoice = false;
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
            System.out.println("Mul: " + showingMultipleChoice);
            System.out.println("Dic: " + showingDictation);
            System.out.println("=============");

            if (showingMultipleChoice && multipleChoiceStage != null && !multipleChoiceStage.isShowing()) {
                multipleChoiceStage.show();
            } else if (showingDictation && dictationStage != null && !dictationStage.isShowing()) {
                dictationStage.show();
            }

            if (!showingMultipleChoice && multipleChoiceStage != null && multipleChoiceStage.isShowing()) {
                processNextExercise();
                multipleChoiceStage.hide();
            }
            if (!showingDictation && dictationStage != null && dictationStage.isShowing()) {
                processNextExercise();
                dictationStage.hide();
            }
        });
    }
}
