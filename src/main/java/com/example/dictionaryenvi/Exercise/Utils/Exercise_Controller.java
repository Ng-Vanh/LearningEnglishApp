package com.example.dictionaryenvi.Exercise.Utils;

import com.backend.Exercise.Utils.Exercise;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Exercise_Controller<T extends Exercise> {
    @FXML
    protected Label question;

    @FXML
    protected Label scoreLabel;

    @FXML
    protected Label questionIndexLabel;

    protected int score = 0;
    protected int questionIndex = 0;

    protected ArrayList<T> exerciseList;

    protected abstract void loadExerciseFromBank();

    @FXML
    public void initialize() {
        loadExerciseFromBank();
        shuffleList();
        generateQuestion();
    }

    protected abstract String getUserAnswer();

    protected abstract void submitAnswer();

    protected void shuffleList() {
        Collections.shuffle(exerciseList);
    }

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
}
