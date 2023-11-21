package com.example.dictionaryenvi.TopicWord;

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
import java.util.Map;

import static com.example.dictionaryenvi.Account.Login.currentUser;
import static com.example.dictionaryenvi.HomePage.HomePage.*;

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
    public static final String topicPhraseVerbs = "PhraseVerbs";
    public static final String topicWeather ="Weather";
    public static final String topicIdiom = "Idiom";
    public static final String topicRandom = "Random";
    public static UserLearnWord currentUserLearnWord = new UserLearnWord(currentUser.getUsername());
    public static LearnedDataAccess learnedDataAccess = new LearnedDataAccess();

    @FXML
    private ProgressBar techPrgBar, sportPrgBar, weatherPrgBar,
            idiomPrgBar, foodPrgBar, businessPrgBar,
            animalPrgBar, plantsPrgBar, fashionPrgBar,
            bodyPrgBar, characterPrgBar, phrasePrgBar;

    public void initialize(){
        Map<String, Integer> listCount = learnedDataAccess.listCountLearnedWords(currentUser.getUsername());
        String[] arrayTopics =new String[] {topicAnimal, topicFood, topicTech, topicSports, topicBody,
                topicPlants, topicFashion, topicCharacter, topicBusiness,
                topicPhraseVerbs, topicWeather, topicIdiom};
        ProgressBar[] arrayProgress = new ProgressBar[] {animalPrgBar, foodPrgBar, techPrgBar, sportPrgBar,bodyPrgBar,
                plantsPrgBar, fashionPrgBar, characterPrgBar, businessPrgBar,
                phrasePrgBar, weatherPrgBar, idiomPrgBar};
        for (int i = 0; i < arrayTopics.length; i++) {
            if(listCount.containsKey(arrayTopics[i])){
                arrayProgress[i].setProgress(listCount.get(arrayTopics[i]) / 30.0);
            }else{
                arrayProgress[i].setProgress(0);
            }
        }
    }
    public void goToHomePage(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/HomePage/HomePage.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Learn/FXML/Learn.fxml"));
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
        currentUserLearnWord.setTopic(topicPhraseVerbs);
        loadCard(mouseEvent);
    }

    public void goToMainDict(MouseEvent mouseEvent) {
        moveToHomePageNavbar(mouseEvent);
    }

    public void goToHome(MouseEvent mouseEvent) {
        moveToHomePageNavbar(mouseEvent);
    }

    public void goToGame(MouseEvent mouseEvent) {
        moveToExerciseNavbar(mouseEvent);
    }

    public void goToLearn(MouseEvent mouseEvent) {
        moveToLearnTopicWordNavbar(mouseEvent);
    }

    public void clickUserInfo(MouseEvent mouseEvent) {
        clickUserInfoNavbar(mouseEvent);
    }

    public void moveToLearnWordOfDay(MouseEvent mouseEvent) {
        moveToLearnWordOfDayNavbar(mouseEvent);
    }
}
