package com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MultipleChoice_Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader MultipleChoice = new FXMLLoader(MultipleChoice_Application.class.getResource("/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/FXML/MultipleChoice.fxml"));

        Scene MultipleChoiceScene = new Scene(MultipleChoice.load(), 960, 576);
        stage.setTitle("Multiple Choice");
        stage.setMinWidth(680);
        stage.setMinHeight(500);
        stage.setScene(MultipleChoiceScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}