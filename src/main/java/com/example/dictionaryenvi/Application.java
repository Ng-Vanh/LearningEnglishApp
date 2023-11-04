package com.example.dictionaryenvi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Exercises/MultipleChoice/MultipleChoice.fxml"));
        FXMLLoader MultipleChoice = new FXMLLoader(Application.class.getResource("/com/example/dictionaryenvi/Exercises/MultipleChoice/FXML/MultipleChoice.fxml"));

        Scene MultileChoiceScene = new Scene(MultipleChoice.load(), 960, 576);
        stage.setTitle("MultipleChoice");
        stage.setScene(MultileChoiceScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}