package com.example.dictionaryenvi.Exercise.ExerciseSelection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseSelection_Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Load the ExerciseSelection.fxml file
        FXMLLoader exerciseSelectionLoader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/ExerciseSelection/FXML/ExerciseSelection.fxml"));
        Parent exerciseSelectionRoot = exerciseSelectionLoader.load();
        Scene exerciseSelectionScene = new Scene(exerciseSelectionRoot, 960, 576);

        // Set the title and scene for the primary stage
        stage.setTitle("Exercise Selection");
        stage.setScene(exerciseSelectionScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
