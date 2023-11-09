package com.example.dictionaryenvi.Exercise.ExerciseSelection;

import com.example.dictionaryenvi.Exercise.Exercises.Dictation.Dictation_Application;
import com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice.MultipleChoice_Application;
import com.example.dictionaryenvi.Exercise.Utils.Scene_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseSelection_Controller extends Scene_Controller {

    @FXML
    private void enter_MultipleChoice(ActionEvent event) throws IOException {
//        MultipleChoice_Application multipleChoice_application = new MultipleChoice_Application();
//        Stage stage = (Stage) ((javafx.scene.Node) (event.getSource())).getScene().getWindow();
//        stage.setTitle("Multiple Choice");
//        multipleChoice_application.start(stage);

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/FXML/MultipleChoice.fxml"));
//        try {
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.setTitle("Multiple Choice");
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        String FXML_Path = "/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/FXML/MultipleChoice.fxml";
        String title = "Multiple Choice";
        enter_newScene(FXML_Path, title, event, true);
    }

    @FXML
    private void enter_Dictation(ActionEvent event) throws IOException {
//        Dictation_Application dictation_application = new Dictation_Application();
//        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//        stage.setTitle("Dictation");
//        dictation_application.start(((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()));

        String FXML_Path = "/com/example/dictionaryenvi/Exercise/Exercises/Dictation/FXML/Dictation.fxml";
        String title = "Dictation";
        enter_newScene(FXML_Path, title, event, true);
    }
}
