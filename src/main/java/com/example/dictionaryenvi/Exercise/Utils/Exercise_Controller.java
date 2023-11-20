package com.example.dictionaryenvi.Exercise.Utils;

import com.backend.Exercise.Utils.Exercise;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    private MediaPlayer correctMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/common/assets/correct.mp3").toString()));
    private MediaPlayer incorrectMediaPlayer = new MediaPlayer(new Media(getClass().getResource("/com/example/dictionaryenvi/Exercise/common/assets/incorrect.mp3").toString()));

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

    protected abstract void extraInit();

    protected String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    public void initialize() {
        extraInit();

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

    protected void showAlert(boolean isCorrect, String correctAnswer, String extraContent) {
        String title;
        String content;
        if (isCorrect) {
            title = "Correct!";
            content = "Congrats, you got a new point!";
        } else {
            title = "Incorrect";
            content = "Sorry, the correct answer is: " + correctAnswer + ".";
        }

        content += "\n\n" + extraContent;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true); // Ensure text wrapping for longer content

        // Set style class for content
        contentLabel.getStyleClass().add("content");

        // Create a VBox to hold the content
        VBox contentLayout = new VBox(contentLabel);
        contentLayout.setSpacing(10); // Add spacing for better layout

        // Create a ScrollPane to make content scrollable if it's too large
        ScrollPane scrollPane = new ScrollPane(contentLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Set the ScrollPane as the content of the DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(scrollPane);

        // Remove the close button
        alert.getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = alert.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.setVisible(false);

        // Set background color based on correctness
        if (isCorrect) {
            dialogPane.getStyleClass().add("correct-alert");
        } else {
            dialogPane.getStyleClass().add("incorrect-alert");
        }

        // Set a style class for the OK button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("ok-button");

        // Set style class for header panel (title)
        Node titleNode = dialogPane.lookup(".header-panel");
        if (titleNode instanceof Label) {
            Label titleLabel = (Label) titleNode;
            titleLabel.getStyleClass().add("header-panel");
        }

        // Load updated CSS file
        String cssFile = getClass().getResource("/com/example/dictionaryenvi/Exercise/common/CSS/Alert.css").toExternalForm();
        dialogPane.getStylesheets().add(cssFile);

        // Fields to store initial position
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];

        // Mouse press event handler to store initial position
        // Mouse press event handler to store initial position and make transparent
        EventHandler<MouseEvent> mousePressedHandler = event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();

            // Reduce opacity when dragging
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.setOpacity(0.7); // Adjust the value as needed
        };

// Mouse drag event handler to adjust the alert position
        EventHandler<MouseEvent> mouseDraggedHandler = event -> {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        };

// Mouse release event handler to reset opacity
        EventHandler<MouseEvent> mouseReleasedHandler = event -> {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            stage.setOpacity(1.0); // Reset to full opacity
        };

// Apply these handlers to the DialogPane
        dialogPane.setOnMousePressed(mousePressedHandler);
        dialogPane.setOnMouseDragged(mouseDraggedHandler);
        dialogPane.setOnMouseReleased(mouseReleasedHandler);

// Optionally, apply these handlers to the ScrollPane and its content
        scrollPane.setOnMousePressed(mousePressedHandler);
        scrollPane.setOnMouseDragged(mouseDraggedHandler);
        scrollPane.setOnMouseReleased(mouseReleasedHandler);
        contentLayout.setOnMousePressed(mousePressedHandler);
        contentLayout.setOnMouseDragged(mouseDraggedHandler);
        contentLayout.setOnMouseReleased(mouseReleasedHandler);


        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();
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

    @FXML
    protected void goBack(MouseEvent event) { // fix this
        String FXML_Path = "/com/example/dictionaryenvi/Exercise/ExerciseSelection/FXML/ExerciseSelection.fxml";
        String title = "Exercise Selection";
        enter_newScene(FXML_Path, title, event, false);
    }
}
