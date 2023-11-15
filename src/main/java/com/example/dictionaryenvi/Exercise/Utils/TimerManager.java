package com.example.dictionaryenvi.Exercise.Utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class TimerManager {
    private Timeline timer;
    private int secondsRemaining;
    private Runnable timeoutAction;

    private Label timerLabel;

    public TimerManager(Label timerLabel, int durationInSeconds, Runnable timeoutAction) {
        this.timeoutAction = timeoutAction;
        this.timerLabel = timerLabel;

        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining--;
            updateTimerLabel();

            if (secondsRemaining <= 0) {
                timer.stop();
                handleTimeout();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        resetTimer(durationInSeconds);
        startTimer();
    }

    public void startTimer() {
        timer.play();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void resetTimer(int durationInSeconds) {
        secondsRemaining = durationInSeconds;
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        if (timerLabel != null) {
            timerLabel.setText("Time remaining: " + secondsRemaining + " seconds");
        }
    }

    private void handleTimeout() {
        if (timeoutAction != null) {
            timeoutAction.run();
        }
    }
}
