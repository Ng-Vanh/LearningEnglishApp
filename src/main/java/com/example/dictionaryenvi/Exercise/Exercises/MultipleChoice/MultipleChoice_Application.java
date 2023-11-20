package com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MultipleChoice_Application extends javafx.application.Application {

    private final String FXML_Path = "/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/FXML/MultipleChoice.fxml";

    private MultipleChoice_Controller controller;

    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader MultipleChoiceFXML = new FXMLLoader(MultipleChoice_Application.class.getResource(FXML_Path));
        Parent root = MultipleChoiceFXML.load();
        this.root = root;
        MultipleChoice_Controller controller = MultipleChoiceFXML.getController();
        this.controller = controller;

        Scene MultipleChoiceScene = new Scene(root, 960, 576);
        stage.setTitle("Multiple Choice");
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        stage.setScene(MultipleChoiceScene);
        stage.show();
    }

    public Parent getRoot() {
        return root;
    }

    public MultipleChoice_Controller getController() {
        return this.controller;
    }

    public static void main(String[] args) {
        launch();
    }
}
