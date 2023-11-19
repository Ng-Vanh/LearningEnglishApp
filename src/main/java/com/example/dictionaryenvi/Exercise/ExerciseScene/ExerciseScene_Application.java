package com.example.dictionaryenvi.Exercise.ExerciseScene;

import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseScene_Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader MultipleChoice = new FXMLLoader(MultipleChoice_Application.class.getResource("/com/example/dictionaryenvi/Exercise/ExerciseScene/FXML/ExerciseScene.fxml"));

        Scene MultipleChoiceScene = new Scene(MultipleChoice.load());
        stage.setScene(MultipleChoiceScene);
    }

    public static void main(String[] args) {
        launch();
    }
}