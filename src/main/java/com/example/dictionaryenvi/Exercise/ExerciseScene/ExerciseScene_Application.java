package com.example.dictionaryenvi.Exercise.ExerciseScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseScene_Application extends javafx.application.Application {

    private final String FXML_Path = "/com/example/dictionaryenvi/Exercise/ExerciseScene/FXML/ExerciseScene.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader exerciseSceneFXML = new FXMLLoader(ExerciseScene_Application.class.getResource(FXML_Path));
        Parent exerciseSceneRoot = exerciseSceneFXML.load();
        Scene exerciseScene = new Scene(exerciseSceneRoot);
        stage.setScene(exerciseScene);

//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
