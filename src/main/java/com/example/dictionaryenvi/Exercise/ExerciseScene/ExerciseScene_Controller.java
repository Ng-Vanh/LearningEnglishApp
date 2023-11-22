package com.example.dictionaryenvi.Exercise.ExerciseScene;

import com.backend.Connection.LearnedDataAccess;
import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Utils.Exercise;
import com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWord;
import com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWord;
import com.backend.User.UserLearnWord;
import com.example.dictionaryenvi.Exercise.Utils.TimerManager;
import com.example.dictionaryenvi.Learning.DailyRandomWordGenerator;
import com.example.dictionaryenvi.Learning.Learn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice.getDefaultMultipleChoice;
import static com.backend.Exercise.Utils.ExerciseLoader.getExerciseListFromSimpleTopicWordList;
//import static com.backend.User.UserStatus.updateScoreStatus;
import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.*;
import static com.backend.User.UserStatus.updateScoreStatus;
import static com.example.dictionaryenvi.Account.Login.currentUser;
import static com.example.dictionaryenvi.Exercise.Utils.GlobalProperties.*;
import static com.example.dictionaryenvi.Learning.Learn.userSimpleTopicWordList;

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

    public static MultipleChoice globalCurrentMultipleChoice = getDefaultMultipleChoice();
    public static Dictation globalCurrentDictation = Dictation.getDefaultDictation();

    public static boolean globalShowingMultipleChoice = false;
    public static boolean globalShowingDictation = false;

    private static boolean startedMultipleChoice = false;
    private static boolean startedDictation = false;

    public static void saveUserScore() { // update score here
        System.out.println("Saved user score: " + globalScore);
        updateScoreStatus(globalScore);
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

    public boolean canGetUser() {
        if (currentUser == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<SimpleTopicWord> getSimpleTopicWordListFromDataAccess() {
        String currentUserStr = currentUser.getUsername();
        ArrayList<UserLearnWord> userLearnWordList = LearnedDataAccess.getInstance().getWordsFollowEachUser(currentUserStr);
        ArrayList<SimpleTopicWord> simpleTopicWordList = new ArrayList<>();
        for (UserLearnWord userLearnWord: userLearnWordList) {
            String topic = userLearnWord.getTopic();
            String word = userLearnWord.getWord();
            SimpleTopicWord simpleTopicWord = new SimpleTopicWord(topic, word);
            simpleTopicWordList.add(simpleTopicWord);
        }
        System.out.println(currentUserStr + ", LENGTH IS : " + simpleTopicWordList);
        return simpleTopicWordList;
    }

    @FXML
    public void initialize() {
        HashSet<SimpleTopicWord> fullSimpleTopicWordSet = new HashSet<>();

        ArrayList<SimpleTopicWord> simpleTopicWordListFromDataAccess = new ArrayList<>();
        ArrayList<SimpleTopicWord> randomWordList = DailyRandomWordGenerator.generateDailyRandomWords(globalFullSimpleTopicWordList);


        if (canGetUser()) {
            System.out.println("Can get user");
            simpleTopicWordListFromDataAccess = new ArrayList<>(getSimpleTopicWordListFromDataAccess());
            System.out.println("User has: " + simpleTopicWordListFromDataAccess);
            System.out.println();
//            fullSimpleTopicWordSet.addAll(simpleTopicWordListFromDataAccess);
        }
        else {
            System.out.println("Cannot get user.");
        }

        if (fullExerciseList == null) {
            fullExerciseList = new ArrayList<>();
        }

        if (fullExerciseList != null || !fullExerciseList.isEmpty()) {
            fullExerciseList.clear();
        }

//        fullExerciseList.add(globalCurrentMultipleChoice);

        if (canGetUser()) {
            fullSimpleTopicWordSet.addAll(simpleTopicWordListFromDataAccess);
//            fullExerciseList.addAll(getExerciseListFromSimpleTopicWordList(simpleTopicWordListFromDataAccess));
        }
        if (!randomWordList.isEmpty()) {
            fullSimpleTopicWordSet.addAll(randomWordList);
//            fullExerciseList.addAll(getExerciseListFromSimpleTopicWordList(randomWordList));
        }
        else {
            fullSimpleTopicWordSet.addAll(globalFullSimpleTopicWordList);
//            fullExerciseList.addAll(getExerciseListFromSimpleTopicWordList(globalFullSimpleTopicWordList));
        }


        ArrayList<SimpleTopicWord> fullSimpleTopicWordList = new ArrayList<>(fullSimpleTopicWordSet);

        fullExerciseList.addAll(getExerciseListFromSimpleTopicWordList(fullSimpleTopicWordList));
        Platform.runLater(() -> {
            exerciseStage = (Stage) dummyLabel.getScene().getWindow();
//            stage.close();

//            fullExerciseList = getExerciseListFromSimpleTopicWordList(globalFullSimpleTopicWordList);


            globalDurations = 60;
            globalIsRunningExercise = true;
            globalExerciseIndex = 0;
            globalScore = 0;
            globalExerciseListSize = fullExerciseList.size();

            Collections.shuffle(fullExerciseList);
            System.out.println("PRINTING EXERCISE TO DO:");

            fullExerciseList.add(getDefaultMultipleChoice());
            System.out.println(fullExerciseList);
            System.out.println("==========");

            setMultipleChoiceCloseCallback(this::processNextExercise);
            setDictationCloseCallback(this::processNextExercise);
            System.out.println("Initial, setting isRunning to TRUE");
            globalIsRunningExerciseProperty.set(true);
            processNextExercise();
        });
    }

    public void processNextExercise() {
        System.out.println("INDEX IS: " + globalExerciseIndex);
        System.out.println("SIZE IS : " + fullExerciseList.size());
        if (!globalIsRunningExerciseProperty.get() || fullExerciseList.isEmpty()) {
            System.out.println("NOT RUNNING EXERCISE ANYMORE!!!!!!!!!!!!");
            globalIsRunningExerciseProperty.set(false);
            globalExerciseIndex = 0;
            saveUserScore();
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

    }
}
