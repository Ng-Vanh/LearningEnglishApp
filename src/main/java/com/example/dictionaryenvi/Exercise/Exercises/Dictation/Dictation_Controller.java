package com.example.dictionaryenvi.Exercise.Exercises.Dictation;

import com.backend.Exercise.Exercises.Dictation.Dictation;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
        submitButton.setOnMouseEntered(event -> {
            playHover();
        });

        audioIcon.setOnMouseEntered(event -> {
            playHover();
        });
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
        globalShowingDictation = true;
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

        globalShowingDictation = false;
        handleScene();
        System.out.println("CLOSING DICTATION");
    }
}
