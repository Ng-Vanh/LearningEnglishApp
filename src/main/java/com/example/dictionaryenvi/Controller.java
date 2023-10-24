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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Controller {
    @FXML
    private WebView showMean;
    @FXML
    private TextField wordTranslate;
    @FXML
    private ListView suggestListWords;
    @FXML
    private Button pronounceBtn, showFavoriteListWord;
    @FXML
    private CheckBox likeCheckBox, disLikeCheckBox;


    private DictionaryManagement myDictionary = new DictionaryManagement();


    private String formatWord(String str){
        String firstLetter = str.substring(0, 1).toUpperCase();
        String lastLetters = str.substring(1).toLowerCase();
        String titleWord = firstLetter + lastLetters;
        return titleWord;
    }
    private void translate(String curWord){
        if(!curWord.equals("")){
            String lowerCaseWord = curWord.toLowerCase();
            String titleWord = formatWord(curWord);
            Word tmpWord = new Word(lowerCaseWord,null);
            Word tmpMeaning = myDictionary.lookupWord(tmpWord);
            boolean isFavoriteWord = myDictionary.checkIsFavoriteWord(tmpMeaning);
            System.out.println(tmpMeaning.getTarget() + ": " + isFavoriteWord);
            if(isFavoriteWord){
                likeCheckBox.setSelected(true);
                disLikeCheckBox.setSelected(false);
            }else{
                likeCheckBox.setSelected(false);
            }
            String meaning = tmpMeaning.getExplain();
            String meaningShow = "<h3 style = 'font-style: normal;'" + meaning + "</h3>";


            String showStr = "<h2 style ='color: red; font-style: italic;'>" + titleWord +"</h2>" + meaningShow;

            if (meaning.equals("not found!")) {
                showMean.getEngine().loadContent(titleWord+ " is not found!!!");
            } else {
                showMean.getEngine().loadContent(showStr);
                Audio audioTranslation = new Audio(lowerCaseWord);
                System.out.println(audioTranslation.getAudioLink());
                pronounceBtn.setVisible(true);
                Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/icon/speaker.jpg").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                pronounceBtn.setGraphic(imageView);
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
        Word tmpWord = new Word(newText);
        ArrayList<Word> listSuggestWord = myDictionary.searchWord(tmpWord);
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

    public void clickLike(ActionEvent event) {
        if(disLikeCheckBox.isSelected()) {
            disLikeCheckBox.setSelected(false);
        }
        String text = wordTranslate.getText();
        Word tmpWord = new Word(text,null);
        Word curWord = myDictionary.lookupWord(tmpWord);
        myDictionary.addFavoriteWord(curWord);
    }

    public void clickDislike(ActionEvent event) {
        if(likeCheckBox.isSelected()) {
            likeCheckBox.setSelected(false);
        }
    }

    public void clickFavoriteListWord(ActionEvent event) {
        pronounceBtn.setVisible(false);
        String showList = "";
        Set<Word> tmp = myDictionary.getFavoriteWord();
        if(tmp.isEmpty()) {
            String ErrorMessage = "<h3 style ='color: red; font-style: italic;'>The favorite list word is null !!!</h3>";
            showMean.getEngine().loadContent(ErrorMessage);
        }
        else{
            int i = 0;
            for(Word w : tmp) {
                i++;
                Word curWord = myDictionary.lookupWord(w);
                String wordEn = curWord.getTarget();
                String wordVi = curWord.getExplain();

                String title = formatWord(wordEn);

                String meaningShow = "<h3 style = 'font-style: normal;'" + wordVi + "</h3>";
                String showStr = "<h2 style ='color: red; font-style: italic;'>" +i+". " + title +"</h2>" + meaningShow;

                showList += showStr ;
                showList += "=========================\n";
            }
            showMean.getEngine().loadContent(showList);
        }

    }
}