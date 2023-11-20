package com.example.dictionaryenvi.Exercise.Utils;

import com.backend.Exercise.Utils.Exercise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import static com.example.dictionaryenvi.Exercise.ExerciseScene.ExerciseScene_Controller.*;

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

    private MediaPlayer correctMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/assets/correct.mp3").toString()));
    private MediaPlayer incorrectMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/assets/incorrect.mp3").toString()));

    protected void playEffect(MediaPlayer player) {
        stopAllEffects();
        player.play();
    }

    protected void stopAllEffects() {
        correctMediaPlayer.stop();
        incorrectMediaPlayer.stop();
    }

    protected void playCorrectEffect() {
        playEffect(correctMediaPlayer);
    }

    protected void playIncorrectEffect() {
        playEffect(incorrectMediaPlayer);
    }

    @FXML
    public void initialize() {

        if (globalTimerLabel == null) {
            globalTimerLabel = new Label();
        }
        if (globalTimerManager == null) {
            globalTimerManager = new TimerManager(globalTimerLabel, globalDurations, this::handleTimeout);
        }

        timerLabel.textProperty().bind(globalTimerLabel.textProperty());
        timerManager = globalTimerManager;

        generateQuestion();
    }

    protected void handleTimeout() {
        System.out.println("HANDLE TIMEOUT HERE");
        globalIsRunningExercise = false;
        saveUserScore();
    }

    protected abstract String getUserAnswer();

    protected abstract void submitAnswer();

    protected abstract Stage getStage();

    protected abstract void setQuestion(T exercise);

    protected abstract void generateQuestion();

    protected void setScoreLabel() {
        this.scoreLabel.setText("Score: " + globalScore);
    }

    protected void setQuestionIndexLabel() {
        this.questionIndexLabel.setText("Question: " + (globalExerciseIndex) + "/" + globalExerciseListSize);
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
    protected void goBack(ActionEvent event) { // fix this
        String FXML_Path = "/com/example/dictionaryenvi/Exercise/ExerciseSelection/FXML/ExerciseSelection.fxml";
        String title = "Exercise Selection";
        enter_newScene(FXML_Path, title, event, false);
    }
}
