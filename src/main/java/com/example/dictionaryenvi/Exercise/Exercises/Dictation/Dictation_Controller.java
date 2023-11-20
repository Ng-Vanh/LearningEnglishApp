package com.example.dictionaryenvi.Exercise.Exercises.Dictation;

import com.backend.Exercise.Exercises.Dictation.Dictation;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static com.example.dictionaryenvi.Exercise.ExerciseScene.ExerciseScene_Controller.*;

public class Dictation_Controller extends Exercise_Controller<Dictation> {
    @FXML
    private Button submitButton;

    @FXML
    private ImageView audioIcon;

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
    protected void extraInit() {

    }

    @Override
    protected void generateQuestion() {
        timerManager.startTimer();
        setQuestion(globalCurrentDictation);
        setScoreLabel();
        setQuestionIndexLabel();
        resetButtonColor(submitButton);
    }

    public void updateQuestion() {
        stopBreathingAnimation();
        generateQuestion();
        showingDictation = true;
        handleScene();
    }

    @FXML
    public void playAudio() {
        String audioUrl = exercise.getAudioTranslation().getAudioLink();
        Media media = new Media(audioUrl);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            stopBreathingAnimation();
        }

        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnPlaying(() -> startBreathingAnimation());

        mediaPlayer.setOnEndOfMedia(() -> {
            stopBreathingAnimation();
        });

        mediaPlayer.play();
    }

    private ScaleTransition breathingAnimation;

    private void startBreathingAnimation() {
        if (breathingAnimation == null) {
            // Set the initial scale to normal
            audioIcon.setScaleX(1.0);
            audioIcon.setScaleY(1.0);

            breathingAnimation = new ScaleTransition(Duration.seconds(1), audioIcon);
            breathingAnimation.setByX(0.5);
            breathingAnimation.setByY(0.5);
            breathingAnimation.setCycleCount(ScaleTransition.INDEFINITE);
            breathingAnimation.setAutoReverse(true);
            breathingAnimation.play();
        }
    }

    private void stopBreathingAnimation() {
        if (breathingAnimation != null) {
            // Set the scale back to normal at the end of the current cycle
            breathingAnimation.setOnFinished(event -> {
                audioIcon.setScaleX(1.0);
                audioIcon.setScaleY(1.0);
            });

            // Stop the animation after the current cycle is complete
            breathingAnimation.stop();

            breathingAnimation = null;
        }
    }
    @Override
    protected String getUserAnswer() {
        return answerTextField.getText();
    }

    private void setButtonColor(Button button, Color color) {
        String style = "-fx-background-color: " + toRGBCode(color) + ";";
        style += "-fx-effect: dropshadow(gaussian, " + toRGBCode(color) + ", 10, 0, 0, 0);";

        button.setStyle(style);
    }

    private void resetButtonColor(Button button) {
        button.setStyle(""); // This resets the style to the default state
    }


    @Override
    @FXML
    protected void submitAnswer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        stopBreathingAnimation();

        String userAnswer = getUserAnswer();
        timerManager.stopTimer();
        // Add logic to handle the submitted answer
        System.out.println("Submitted answer: " + userAnswer);
        if (userAnswer != null) {
            if (exercise.isCorrect(userAnswer)) {
                playCorrectEffect();
                globalScore += 1;
                System.out.println("Correct!");
                setButtonColor(submitButton, Color.GREEN);
                showAlert(true, exercise.getCorrectAnswer(), exercise.getAudioTranslation().getTranslation());

            } else {
                playIncorrectEffect();
                setButtonColor(submitButton, Color.RED);
                System.out.println("Incorrect, the correct answer is " + exercise.getCorrectAnswer() + ".");
                showAlert(false, exercise.getCorrectAnswer(), exercise.getAudioTranslation().getTranslation());
            }
            generateQuestion();
        } else {
            System.out.println("Please enter valid answer");
        }

        answerTextField.clear();

//        Stage stage = getStage();
//        stage.hide();
        showingDictation = false;
        handleScene();
        System.out.println("CLOSING DICTATION");
    }

//    @Override
//    protected void showAlert(String title, String content, boolean isCorrect) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//
//        // Content with explanation
//        String fullContent = content + "\n\n" + exercise.getAudioTranslation().getTranslation();
//        Label contentLabel = new Label(fullContent);
//        contentLabel.setWrapText(true); // Ensure text wrapping for longer content
//
//        // Set style class for content
//        contentLabel.getStyleClass().add("content");
//
//        // Create a VBox to hold the content
//        VBox contentLayout = new VBox(contentLabel);
//        contentLayout.setSpacing(10); // Add spacing for better layout
//
//        // Create a ScrollPane to make content scrollable if it's too large
//        ScrollPane scrollPane = new ScrollPane(contentLayout);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
//
//        // Set the ScrollPane as the content of the DialogPane
//        alert.getDialogPane().setContent(scrollPane);
//
//        // Remove the close button
//        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//        alert.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);
//
//        // Set the alert as draggable
//        makeAlertDraggable(alert);
//
//        // Set background color based on correctness
//        if (isCorrect) {
//            alert.getDialogPane().getStyleClass().add("correct-alert");
//        } else {
//            alert.getDialogPane().getStyleClass().add("incorrect-alert");
//        }
//
//        // Set a style class for the OK button
//        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
//        okButton.getStyleClass().add("ok-button");
//
//        // Set style class for header panel (title)
//        Node titleNode = alert.getDialogPane().lookup(".header-panel");
//        if (titleNode instanceof Label) {
//            Label titleLabel = (Label) titleNode;
//            titleLabel.getStyleClass().add("header-panel");
//        }
//
//        // Load updated CSS file
//        String cssFile = getClass().getResource("/com/example/dictionaryenvi/Exercise/common/CSS/Alert.css").toExternalForm();
//        alert.getDialogPane().getStylesheets().add(cssFile);
//
//        alert.initStyle(StageStyle.TRANSPARENT);
//
//        // Show the alert and wait for user interaction
//        alert.showAndWait();
//    }


}
