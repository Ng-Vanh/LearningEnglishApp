package com.example.dictionaryenvi.Exercise.ExerciseScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseScene_Application extends javafx.application.Application {
    public static Stage mainStage;

    private final String FXML_Path = "/com/example/dictionaryenvi/Exercise/ExerciseScene/FXML/ExerciseScene.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader ExerciseFXML = new FXMLLoader(ExerciseScene_Application.class.getResource(FXML_Path));
        ExerciseFXML.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
