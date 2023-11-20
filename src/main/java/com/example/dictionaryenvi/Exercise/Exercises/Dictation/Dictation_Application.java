package com.example.dictionaryenvi.Exercise.Exercises.Dictation;

import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Dictation_Application extends javafx.application.Application {

    private final String FXML_Path = "/com/example/dictionaryenvi/Exercise/Exercises/Dictation/FXML/Dictation.fxml";

    private Dictation_Controller controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader DictationFXML = new FXMLLoader(Dictation_Application.class.getResource(FXML_Path));
        Parent root = DictationFXML.load();
        Dictation_Controller controller = DictationFXML.getController();
        this.controller = controller;

        Scene DictationScene = new Scene(root, 960, 576);
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        stage.setTitle("Dictation");
        stage.setScene(DictationScene);
        stage.show();
    }

    public Dictation_Controller getController() {
        return this.controller;
    }

    public static void main(String[] args) {
        launch();
    }
}
