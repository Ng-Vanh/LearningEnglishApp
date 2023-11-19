package com.example.dictionaryenvi.Exercise.Utils;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Scene_Controller {

    public Scene_Controller() {

    }

    public void enter_newScene(String FXML_Path, String title, ActionEvent event, boolean slideFromRight) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_Path));
        try {
            Parent root = loader.load();

            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();

            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(javafx.scene.paint.Color.TRANSPARENT);
            ImageView snapshot = new ImageView(currentScene.getRoot().snapshot(snapshotParameters, null));

            StackPane rootPane = new StackPane();
            rootPane.getChildren().addAll(snapshot, root);

            double sceneWidth = currentScene.getWidth();
            root.translateXProperty().set(slideFromRight ? sceneWidth : -sceneWidth); // Initial position off the scene

            Scene newScene = new Scene(rootPane, sceneWidth, currentScene.getHeight());

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), snapshot);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                stage.setScene(newScene);
                stage.setTitle(title);

                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), root);
                transition.setToX(0);
                transition.play();

                // Apply a bouncing effect with acceleration and deceleration
                transition.setOnFinished(eventTransition -> {
                    Timeline timeline = new Timeline();
                    KeyValue keyValue = new KeyValue(root.translateXProperty(), slideFromRight ? -30 : 30);
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(70), keyValue);
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.setAutoReverse(true);
                    timeline.setCycleCount(2);
                    timeline.play();
                });
            });
            fadeOut.play();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
