package com.example.dictionaryenvi.Exercise.Utils;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Scene_Controller {

    public Scene_Controller() {

    }

//    public void enter_newScene(String FXML_Path, String title, ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_Path));
//        try {
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//
//            stage.setTitle(title);
//
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

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

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), snapshot);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                stage.setScene(newScene);
                stage.setTitle(title);

                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), root);
                transition.setToX(0);
                transition.setOnFinished(eventTransition -> rootPane.getChildren().remove(snapshot)); // Remove snapshot after transition
                transition.play();
            });
            fadeOut.play();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    public void enter_newScene(String FXML_Path, String title, ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_Path));
//        try {
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//
//            // Get the current stage
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            // Create a translate transition
//            TranslateTransition transition = new TranslateTransition(Duration.seconds(4), root);
//            transition.setFromX(stage.getWidth()); // Sliding from the right (assuming stage width is the X direction)
//            transition.setToX(0); // Sliding to the left
//
//            // Play the transition
//            transition.play();
//
//            // Set the scene after the transition
//            transition.setOnFinished(e -> {
//                stage.setScene(scene);
//                stage.setTitle(title);
//                stage.show();
//            });
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
