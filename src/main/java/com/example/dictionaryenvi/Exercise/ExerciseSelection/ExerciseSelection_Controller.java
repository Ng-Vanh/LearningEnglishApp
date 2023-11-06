package com.example.dictionaryenvi.Exercise.ExerciseSelection;

import com.example.dictionaryenvi.Exercise.Exercises.Dictation.Dictation_Application;
import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseSelection_Controller {

    @FXML
    private void enter_MultipleChoice(ActionEvent event) throws IOException {
        MultipleChoice_Application multipleChoice_application = new MultipleChoice_Application();
        multipleChoice_application.start(((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()));
    }

    @FXML
    private void enter_Dictation(ActionEvent event) throws IOException {
        Dictation_Application dictation_application = new Dictation_Application();
        dictation_application.start(((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()));
    }
}
