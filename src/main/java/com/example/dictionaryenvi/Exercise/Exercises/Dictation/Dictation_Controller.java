package com.example.dictionaryenvi.Exercise.Exercises.Dictation;

import com.backend.Exercise.Exercises.Dictation.Dictation;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.example.dictionaryenvi.Exercise.ExerciseScene.ExerciseScene_Controller.globalCurrentExercise;
import static com.example.dictionaryenvi.Exercise.ExerciseScene.ExerciseScene_Controller.globalScore;

public class Dictation_Controller extends Exercise_Controller<Dictation> {
    @FXML
    private TextField answerTextField;

    private Dictation exercise;

    private MediaPlayer mediaPlayer;

    @Override
    protected Stage getStage() {
        return (Stage) answerTextField.getScene().getWindow();
    }

    private void setQuestion(String question) {
        this.question.setText(question);
    }

    @Override
    protected void setQuestion(Dictation dictation) {
        setQuestion(dictation.getSentenceWithBlank());
        this.exercise = dictation;
    }

    @Override
    protected void generateQuestion() {
        timerManager.startTimer();
        setQuestion((Dictation) globalCurrentExercise);
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
    }

    @Override
    protected String getUserAnswer() {
        return answerTextField.getText();
    }

    @Override
    @FXML
    protected void submitAnswer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String userAnswer = getUserAnswer();
        timerManager.stopTimer();
        // Add logic to handle the submitted answer
        System.out.println("Submitted answer: " + userAnswer);
        if (userAnswer != null) {
            if (exercise.isCorrect(userAnswer)) {
                playCorrectEffect();
                globalScore += 1;
                System.out.println("Correct!");
                showAlert("Correct!", "Congrats, you got a new point!", true);

            } else {
                playIncorrectEffect();
                System.out.println("Incorrect, the correct answer is " + exercise.getCorrectAnswer() + ".");
                showAlert("Incorrect", "Sorry, the correct answer is " + "'" + exercise.getCorrectAnswer() + "'" + ".", false);
            }
            generateQuestion();
        } else {
            System.out.println("Please enter valid answer");
        }

        answerTextField.clear();

        Stage stage = getStage();
        stage.close();
    }

    @Override
    protected void showAlert(String title, String content, boolean isCorrect) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Create a Text node for content with wrapping
        Text text = new Text(content + "\n\n" + exercise.getAudioTranslation().getTranslation());
        text.setWrappingWidth(500); // Set the preferred width for text wrapping
        text.setStyle("-fx-fill: white;");

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

        // Bind the alert height to the text height
        alert.getDialogPane().prefHeightProperty().bind(Bindings.createDoubleBinding(() ->
                text.getBoundsInLocal().getHeight() + 40, text.boundsInLocalProperty()));

        // Set the content as the Text node
        alert.getDialogPane().setContent(text);

        alert.setWidth(500);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Show the alert and wait for user interaction
        alert.showAndWait();
    }

}
