package com.example.dictionaryenvi.Exercises.Dictation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Dictation_Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Exercises/MultipleChoice/MultipleChoice.fxml"));
        FXMLLoader MultipleChoice = new FXMLLoader(com.example.dictionaryenvi.Exercises.Dictation.Dictation_Application.class.getResource("/com/example/dictionaryenvi/Exercises/Dictation/FXML/Dictation.fxml"));

        Scene MultileChoiceScene = new Scene(MultipleChoice.load(), 960, 576);
        stage.setTitle("Dictation");
        stage.setScene(MultileChoiceScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}