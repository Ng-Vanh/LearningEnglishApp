package com.example.dictionaryenvi.Exercise.ExerciseScene;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Utils.Exercise;
import com.example.dictionaryenvi.Exercise.Utils.TimerManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.backend.Exercise.Utils.ExerciseLoader.getExerciseListFromSimpleTopicWordList;
import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.globalFullSimpleTopicWordList;
//import static com.backend.User.UserStatus.updateScoreStatus;
import static com.example.dictionaryenvi.Exercise.Utils.GlobalProperties.*;

public class ExerciseScene_Controller {

    @FXML
    public Label dummyLabel;

    public static Label globalTimerLabel;
    public static TimerManager globalTimerManager;

    private static ArrayList<Exercise> fullExerciseList;

    private static Exercise globalCurrentExercise;
    public static boolean globalIsRunningExercise;

    public static int globalExerciseIndex;
    public static int globalExerciseListSize;
    public static int globalScore;
    public static int globalDurations;

    public static MultipleChoice globalCurrentMultipleChoice = MultipleChoice.getDefaultMultipleChoice();
    public static Dictation globalCurrentDictation = Dictation.getDefaultDictation();

    public static boolean globalShowingMultipleChoice = false;
    public static boolean globalShowingDictation = false;

    private static boolean startedMultipleChoice = false;
    private static boolean startedDictation = false;

    public static void saveUserScore() { // update score here
        System.out.println("Saved user score: " + globalScore);
//        updateScoreStatus(globalScore);
    }

    private void loadMultipleChoiceScene() {
        if (!globalIsRunningExerciseProperty.get() || fullExerciseList.isEmpty()) {
            System.out.println("NOT RUNNING EXERCISE ANYMORE!!!!!!!!!!!!");
            globalIsRunningExerciseProperty.set(false);
            return;
        }

        try {
            FXMLLoader multipleChoiceLoader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/FXML/MultipleChoice.fxml"));
            Parent multipleChoiceRoot = multipleChoiceLoader.load();
            Scene multipleChoiceScene = new Scene(multipleChoiceRoot);

            exerciseStage.setScene(multipleChoiceScene);

            exerciseStage.setOnCloseRequest(event -> {
                globalIsRunningExerciseProperty.set(false);
                globalShowingDictationProperty.set(false);
                globalShowingMultipleChoiceProperty.set(false);
            });

            if (!globalIsRunningExerciseProperty.get() || fullExerciseList.isEmpty()) {
                System.out.println("NOT RUNNING EXERCISE ANYMORE!!!!!!!!!!!!");
                globalIsRunningExerciseProperty.set(false);
                return;
            }
//
            exerciseStage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDictationScene() {
        if (!globalIsRunningExerciseProperty.get() || fullExerciseList.isEmpty()) {
            System.out.println("NOT RUNNING EXERCISE ANYMORE!!!!!!!!!!!!");
            globalIsRunningExerciseProperty.set(false);
            return;
        }

        try {
            FXMLLoader dictationLoader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/Exercises/Dictation/FXML/Dictation.fxml"));
            Parent dictationRoot = dictationLoader.load();
            Scene dictationScene = new Scene(dictationRoot);

            exerciseStage.setScene(dictationScene);

            exerciseStage.setOnCloseRequest(event -> {
                globalIsRunningExerciseProperty.set(false);
                globalShowingDictationProperty.set(false);
                globalShowingMultipleChoiceProperty.set(false);
            });

            if (!globalIsRunningExerciseProperty.get() || fullExerciseList.isEmpty()) {
                System.out.println("NOT RUNNING EXERCISE ANYMORE!!!!!!!!!!!!");
                globalIsRunningExerciseProperty.set(false);
                return;
            }

            exerciseStage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stage exerciseStage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            exerciseStage = (Stage) dummyLabel.getScene().getWindow();
//            stage.close();

            fullExerciseList = getExerciseListFromSimpleTopicWordList(globalFullSimpleTopicWordList);

            globalDurations = 2;
            globalIsRunningExercise = true;
            globalExerciseIndex = 0;
            globalScore = 0;
            globalExerciseListSize = fullExerciseList.size();

            Collections.shuffle(fullExerciseList);

            setMultipleChoiceCloseCallback(this::processNextExercise);
            setDictationCloseCallback(this::processNextExercise);
            System.out.println("Initial, setting isRunning to TRUE");
            globalIsRunningExerciseProperty.set(true);
            processNextExercise();
        });
    }

    public void processNextExercise() {
        if (!globalIsRunningExerciseProperty.get() || fullExerciseList.isEmpty()) {
            System.out.println("NOT RUNNING EXERCISE ANYMORE!!!!!!!!!!!!");
            globalIsRunningExerciseProperty.set(false);
            return;
        }

//        Platform.runLater(() -> {


        globalCurrentExercise = fullExerciseList.remove(0);
        globalExerciseIndex += 1;
        System.out.println("Question index: " + globalExerciseIndex);
        System.out.println("Processing next exercise: " + globalCurrentExercise);

        if (globalCurrentExercise instanceof MultipleChoice) {
            System.out.println("loading multiple");
            System.out.println("STATUS IS :" + globalIsRunningExerciseProperty.get());
            globalCurrentMultipleChoice = (MultipleChoice) globalCurrentExercise;

            Platform.runLater(() -> {
                globalShowingMultipleChoiceProperty.set(true);
                globalShowingDictationProperty.set(false);
            });

            loadMultipleChoiceScene();
        } else if (globalCurrentExercise instanceof Dictation) {
            System.out.println("loading dictation");
            System.out.println("STATUS IS :" + globalIsRunningExerciseProperty.get());
            globalCurrentDictation = (Dictation) globalCurrentExercise;

            Platform.runLater(() -> {
                globalShowingMultipleChoiceProperty.set(false);
                globalShowingDictationProperty.set(true);
            });

            loadDictationScene();
        }
//        });

    }
}
