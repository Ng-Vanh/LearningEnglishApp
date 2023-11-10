package com.example.dictionaryenvi;

import com.backend.User.UserLearnWord;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.dictionaryenvi.Login.currentUser;

public class TopicWord {
    public static final String topicAnimal = "topicAnimal";
    public static final String topicFood = "topicFood";
    public static final String topicTech = "topicTech";
    public static final String topicSport = "topicSport";
    public static UserLearnWord currentUserLearnWord = new UserLearnWord(currentUser.getUsername());
    public void goToHomePage(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage/HomePage.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToLearnAnimalWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicAnimal);
        System.out.println(currentUserLearnWord.getUsername() + ": " + currentUserLearnWord.getTopic());
    }

    public void goToLearnFoodWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicFood);
        System.out.println(currentUserLearnWord.getUsername() + ": " + currentUserLearnWord.getTopic());

    }

    public void goToLearnTechWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicTech);
        System.out.println(currentUserLearnWord.getUsername() + ": " + currentUserLearnWord.getTopic());
    }

    public void goToLearnSportWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicSport);
        System.out.println(currentUserLearnWord.getUsername() + ": " + currentUserLearnWord.getTopic());
    }
}
