package com.example.dictionaryenvi.Exercises.Dictation;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.backend.Exercise.Exercises.Dictation.Dictation.loadFromBank;

import java.util.ArrayList;
import java.util.Collections;

public class Dictation_Controller {
    @FXML
    private Label questionIndexLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label question;

    @FXML
    private TextField answerTextField;

    private Dictation exercise;

    private int score = 0;
    private int questionIndex = 0;

    private MediaPlayer mediaPlayer;
    private boolean submitted = false;

    private ArrayList<Dictation> exerciseList = new ArrayList<>(loadFromBank());

    private void shuffleList() {
        Collections.shuffle(exerciseList);
    }

    @FXML
    public void initialize() {
        shuffleList();
        generateQuestion();
    }

    private void setQuestion(String question) {
        this.question.setText(question);
    }

    private void setQuestion(Dictation dictation) {
        setQuestion(dictation.getSentenceWithBlank());
        this.exercise = dictation;
    }

    private void generateQuestion() {
        setQuestion(exerciseList.get(questionIndex));
        setScoreLabel();
        setQuestionIndexLabel();
    }

    public void setScoreLabel() {
        this.scoreLabel.setText("Score: " + score);
    }

    public void setQuestionIndexLabel() {
        this.questionIndexLabel.setText("Question: " + (questionIndex + 1) + "/" + exerciseList.size());
    }

    @FXML
    public void playAudio() {
        String audioUrl = exercise.getAudioTranslation().getAudioLink();
        Media media = new Media(audioUrl);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        submitted = false;
    }

    public void submitAnswer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String userAnswer = answerTextField.getText();
        // Add logic to handle the submitted answer
        System.out.println("Submitted answer: " + userAnswer);
        if (userAnswer != null) {
            if (exercise.isCorrect(userAnswer)) {
                score += 1;
                System.out.println("Correct!");
                showAlert("Correct!", "Congrats, you got a new point!", true);

            } else {
                System.out.println("Incorrect, the correct answer is " + exercise.getCorrectAnswer() + ".");
                showAlert("Incorrect", "Sorry, the correct answer is " + "'" + exercise.getCorrectAnswer() + "'" + ".", false);
            }
            questionIndex += 1;
            generateQuestion();
        } else {
            System.out.println("Please enter valid answer");
        }

        submitted = true;
        answerTextField.clear();
    }

    private void showAlert(String title, String content, boolean isCorrect) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content + "\n\n" + exercise.getAudioTranslation().getTranslation() + ".");

        // Remove the close button
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);

        // Set the alert as draggable
        makeAlertDraggable(alert);

        // Set background color based on correctness
        if (isCorrect) {
            alert.getDialogPane().getStyleClass().add("correct-alert");
        } else {
            alert.getDialogPane().getStyleClass().add("incorrect-alert");
        }

        // Set a style class for the OK button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("ok-button");

        // Load updated CSS file
        String cssFile = getClass().getResource("/com/example/dictionaryenvi/Exercises/Dictation/CSS/Alert.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssFile);

        alert.setWidth(550);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Show the alert and wait for user interaction
        alert.showAndWait();
    }


    private double xOffset = 0;
    private double yOffset = 0;

    private void makeAlertDraggable(Alert alert) {
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
