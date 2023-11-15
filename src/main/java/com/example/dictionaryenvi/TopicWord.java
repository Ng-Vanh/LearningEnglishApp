package com.example.dictionaryenvi;

import javafx.scene.control.ProgressBar;
import com.backend.Connection.LearnedDataAccess;
import com.backend.User.UserLearnWord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.dictionaryenvi.Login.currentUser;

public class TopicWord {
    public static final String topicAnimal = "Animal";
    public static final String topicFood = "Food";
    public static final String topicTech = "Tech";
    public static final String topicSports = "Sports";
    public static final String topicBody = "Body";
    public static final String topicPlants = "Plants";
    public static final String topicFashion = "Fashion";
    public static final String topicCharacter = "Character";
    public static final String topicBusiness = "Business";
    public static final String topicPharseVerbs = "PharseVerbs";
    public static final String topicWeather ="Weather";
    public static final String topicIdiom = "Idiom";
    public static UserLearnWord currentUserLearnWord = new UserLearnWord(currentUser.getUsername());
    public static LearnedDataAccess learnedDataAccess = new LearnedDataAccess();

    @FXML
    private ProgressBar techPrgBar, sportPrgBar, weatherPrgBar,
            idiomPrgBar, foodPrgBar, businessPrgBar,
            animalPrgBar, plantsPrgBar, fashionPrgBar,
            bodyPrgBar, charaterPrgBar, phrasePrgBar;

    public void initialize(){
        techPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicTech) / 30.0);
        sportPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicSports) / 30.0);
        weatherPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicWeather) / 30.0);
        idiomPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicIdiom) / 30.0);
        foodPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicFood) / 30.0 );
        businessPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicBusiness) / 30.0 );
        animalPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicAnimal) / 30.0);
        plantsPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicPlants) / 30.0);
        fashionPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicFashion) / 30.0);
        bodyPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicBody) / 30.0);
        charaterPrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicCharacter) / 30.0);
        phrasePrgBar.setProgress(learnedDataAccess.countLearnedWord(currentUser.getUsername(), topicPharseVerbs) / 30.0);
    }
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
    private void loadCard(MouseEvent mouseEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Learn/Learn.fxml"));
        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToLearnAnimalWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicAnimal);
        loadCard(mouseEvent);
    }

    public void goToLearnFoodWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicFood);
        loadCard(mouseEvent);
    }

    public void goToLearnTechWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicTech);
        loadCard(mouseEvent);
    }

    public void goToLearnSportWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicSports);
        loadCard(mouseEvent);
    }

    public void goToLearnWeatherWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicWeather);
        loadCard(mouseEvent);

    }

    public void goToLearnIdiom(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicIdiom);
        loadCard(mouseEvent);
    }

    public void goToLearnBusinessWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicBusiness);
        loadCard(mouseEvent);
    }

    public void goToLearnPlantsWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicPlants);
        loadCard(mouseEvent);
    }

    public void goToLearnFashionWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicFashion);
        loadCard(mouseEvent);
    }

    public void goToLearnBodyPpWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicBody);
        loadCard(mouseEvent);
    }

    public void goToLearnCharacterWord(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicCharacter);
        loadCard(mouseEvent);
    }

    public void goToLearnPhrasalVerb(MouseEvent mouseEvent) {
        currentUserLearnWord.setTopic(topicPharseVerbs);
        loadCard(mouseEvent);
    }
}
