package com.example.dictionaryenvi.Exercise.Exercises.Dictation;

import com.backend.Exercise.Exercises.Dictation.Dictation;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.StageStyle;

public class Dictation_Controller extends Exercise_Controller<Dictation> {
    @FXML
    private TextField answerTextField;

    private Dictation exercise;

    private MediaPlayer mediaPlayer;
    private boolean submitted = false;

    private MediaPlayer correctMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/assets/incorrect.mp3").toString()));
    private MediaPlayer incorrectMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/assets/incorrect.mp3").toString()));

    @Override
    protected void loadExerciseFromBank() {
        exerciseList = Dictation.loadFromBank();
    }

    private void setQuestion(String question) {
        this.question.setText(question);
    }

    protected void setQuestion(Dictation dictation) {
        setQuestion(dictation.getSentenceWithBlank());
        this.exercise = dictation;
    }

    @Override
    protected void generateQuestion() {
        setQuestion(exerciseList.get(questionIndex));
        setScoreLabel();
        setQuestionIndexLabel();
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

    @Override
    protected String getUserAnswer() {
        String userAnswer = answerTextField.getText();
        return userAnswer;
    }

    @Override
    @FXML
    protected void submitAnswer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String userAnswer = getUserAnswer();
        // Add logic to handle the submitted answer
        System.out.println("Submitted answer: " + userAnswer);
        if (userAnswer != null) {
            if (exercise.isCorrect(userAnswer)) {
                score += 1;
                System.out.println("Correct!");
                showAlert("Correct!", "Congrats, you got a new point!", true);

            } else {
                correctMediaPlayer.stop();
                incorrectMediaPlayer.stop();
                incorrectMediaPlayer.play();
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

    @Override
    protected void showAlert(String title, String content, boolean isCorrect) {
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
        String cssFile = getClass().getResource("/com/example/dictionaryenvi/Exercise/Exercises/Dictation/CSS/Alert.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssFile);

        alert.setWidth(550);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Show the alert and wait for user interaction
        alert.showAndWait();
    }
}
