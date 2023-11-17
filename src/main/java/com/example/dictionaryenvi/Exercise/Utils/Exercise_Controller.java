package com.example.dictionaryenvi.Exercise.Utils;

import com.backend.Exercise.Utils.Exercise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Exercise_Controller<T extends Exercise> extends Scene_Controller {
    @FXML
    protected Label timerLabel;

    protected TimerManager timerManager;

    @FXML
    protected Label question;

    @FXML
    protected Label scoreLabel;

    @FXML
    protected Label questionIndexLabel;

    protected int score = 0;
    protected int questionIndex = 0;

    protected ArrayList<T> exerciseList;

    private MediaPlayer correctMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/assets/correct.mp3").toString()));
    private MediaPlayer incorrectMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/assets/incorrect.mp3").toString()));

    protected void playEffect(MediaPlayer player) {
        stopAllEffects();
        player.play();
    }

    protected void stopAllEffects() {
        correctMediaPlayer.stop();;
        incorrectMediaPlayer.stop();
    }

    protected void playCorrectEffect() {
        playEffect(correctMediaPlayer);
    }

    protected void playIncorrectEffect() {
        playEffect(incorrectMediaPlayer);
    }

    protected abstract void loadExerciseFromBank();

    @FXML
    public void initialize() {
        loadExerciseFromBank();
        shuffleList();

        timerManager = new TimerManager(timerLabel, 60, this::handleTimeout);
        generateQuestion();
    }

    protected abstract void handleTimeout();

    protected abstract String getUserAnswer();

    protected abstract void submitAnswer();

    protected void shuffleList() {
        Collections.shuffle(exerciseList);
    }

    protected abstract void setQuestion(T exercise);

    protected abstract void generateQuestion();

    protected void setScoreLabel() {
        this.scoreLabel.setText("Score: " + score);
    }

    protected void setQuestionIndexLabel() {
        this.questionIndexLabel.setText("Question: " + (questionIndex + 1) + "/" + exerciseList.size());
    }

    protected abstract void showAlert(String title, String content, boolean isCorrect);

    protected double xOffset = 0;
    protected double yOffset = 0;

    protected void makeAlertDraggable(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        alert.getDialogPane().setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        alert.getDialogPane().setOnMouseDragged(e -> {
            stage.setOpacity(0.8);
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });

        alert.getDialogPane().setOnMouseReleased(e -> {
            stage.setOpacity(1.0);
        });
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        // Load ExerciseSelection FXML
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/ExerciseSelection/FXML/ExerciseSelection.fxml"));
//        Parent root = loader.load();
//
//        // Set the new scene
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Exercise Selection");
//        stage.show();
        String FXML_Path = "/com/example/dictionaryenvi/Exercise/ExerciseSelection/FXML/ExerciseSelection.fxml";
        String title = "Exercise Selection";
        enter_newScene(FXML_Path, title, event, false);
    }

//    protected void enter_newScene(String FXML_Path, String title, ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_Path));
//        try {
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.setTitle(title);
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
