package com.example.dictionaryenvi;

import com.backend.Connection.WordDataAccess;
import com.backend.LocalDictionary.Dictionary.DictionaryManagement;
import com.backend.LocalDictionary.Dictionary.Word;
import com.backend.OnlineDictionary.Utils.Audio;
import com.backend.OnlineDictionary.Utils.AudioTranslation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private WebView showMean;
    @FXML
    private TextField wordTranslate;
    @FXML
    private ListView suggestListWords;
    @FXML
    private Button pronounceBtn;

    private DictionaryManagement myDictionary = new DictionaryManagement();

    private void translate(String curWord){
        if(!curWord.equals("")){
            String lowerCaseWord = curWord.toLowerCase();
            String firstLetter = curWord.substring(0, 1).toUpperCase();
            String lastLetters = curWord.substring(1).toLowerCase();
            String titleWord = firstLetter + lastLetters;
            String meaning = myDictionary.dictionaryLookup(lowerCaseWord);
            String meaningShow = "<h3 style = 'font-style: normal;'" + meaning + "</h3>";


            String showStr = "<h2 style ='color: red; font-style: italic;'>" + titleWord +"</h2>" + meaningShow;

            if (meaning.equals("not found") || curWord.equals("")) {
                showMean.getEngine().loadContent(titleWord+ " is not found!!!");
            } else {
                showMean.getEngine().loadContent(showStr);
                Audio audioTranslation = new Audio(lowerCaseWord);
                System.out.println(audioTranslation.getAudioLink());
                pronounceBtn.setVisible(true);

                pronounceBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Media media = new Media(audioTranslation.getAudioLink());
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.play();
                    }
                });
            }
        }else{
            showMean.getEngine().loadContent("Not found English word!!!");
        }


    }
    @FXML
    public void clickTranslate(ActionEvent event){
        String curWord = wordTranslate.getText();
        translate(curWord);
    }
    public void submitTranslate(KeyEvent keyEvent) {
        String curWord = wordTranslate.getText();
        if(keyEvent.getCode() == KeyCode.ENTER){
            translate(curWord);
        }
    }

    public void goToHome(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
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


    public void getSuggestListWord(KeyEvent keyEvent) {
        String newText = wordTranslate.getText();
        System.out.println(newText);
        ArrayList<Word> listSuggestWord = myDictionary.searcher(newText);
        ArrayList<String> listSuggest = new ArrayList<String>();
        for (Word word : listSuggestWord) {
            listSuggest.add(word.getTarget());
        }
        ObservableList<String> items = FXCollections.observableArrayList(listSuggest);
        suggestListWords.setItems(items);

        suggestListWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!suggestListWords.getSelectionModel().isEmpty()) {
                    String selectedWord = (String) suggestListWords.getSelectionModel().getSelectedItem();
                    wordTranslate.setText(selectedWord);
                    translate(selectedWord);
                }
            }
        });
    }
}