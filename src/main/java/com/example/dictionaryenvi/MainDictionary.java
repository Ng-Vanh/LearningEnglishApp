package com.example.dictionaryenvi;

import com.backend.LocalDictionary.Dictionary.DictionaryManagement;
import com.backend.LocalDictionary.Dictionary.Word;
import com.backend.OnlineDictionary.Utils.Audio;
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

public class MainDictionary {
    @FXML
    private WebView showMean;
    @FXML
    private TextField wordTranslate;
    @FXML
    private ListView suggestListWords, listSpecialWord;
    @FXML
    private Button pronounceBtn, showFavoriteListWord, favoriteBtn, historyBtn;
    @FXML
    private ImageView removeBtn;


    private DictionaryManagement myDictionary = new DictionaryManagement();


    /**
     * The function format word to the first letter is Upper Case,
     * and other characters is upper Case.
     *
     * @param str is input string.
     * @return the word is formated according.
     */
    private String formatWord(String str) {
        String firstLetter = str.substring(0, 1).toUpperCase();
        String lastLetters = str.substring(1).toLowerCase();
        String titleWord = firstLetter + lastLetters;
        return titleWord;
    }

    /**
     * The function translate word.
     *
     * @param curWord
     */
    private void translate(String curWord) {
        favoriteBtn.setVisible(true);
        removeBtn.setVisible(true);
        if (!curWord.equals("")) {
            String lowerCaseWord = curWord.toLowerCase();
            String titleWord = formatWord(curWord);
            Word tmpWord = new Word(lowerCaseWord, null);
            Word tmpMeaning = myDictionary.lookupWord(tmpWord);
            boolean isFavoriteWord = myDictionary.checkIsFavoriteWord(tmpMeaning);
            System.out.println(tmpMeaning.getTarget() + ": " + isFavoriteWord);

            if (isFavoriteWord) {
                Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/goldStar.jpg").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                favoriteBtn.setGraphic(imageView);
            } else {
                Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/grayStar.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                favoriteBtn.setGraphic(imageView);
            }

            String meaning = tmpMeaning.getExplain();
            String meaningShow = "<h3 style = 'font-style: normal;'" + meaning + "</h3>";


            String showStr = "<h2 style ='color: red; font-style: italic;'>" + titleWord + "</h2>" + meaningShow;

            if (meaning.equals("not found!")) {
                favoriteBtn.setVisible(false);
                pronounceBtn.setVisible(false);
                removeBtn.setVisible(false);
                String loadSentence = '"' + titleWord  + '"'+ " is not found!!!";
                showMean.getEngine().loadContent("<h3 style='font-style: bold; text-align: center;margin-top:18px;color: red;'>" + loadSentence + "</h3>");
            } else {
                myDictionary.addHistorySearch(tmpMeaning);
                showMean.getEngine().loadContent(showStr);
                Audio audioTranslation = new Audio(lowerCaseWord);
                System.out.println(audioTranslation.getAudioLink());
                pronounceBtn.setVisible(true);
                Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/speaker.jpg").toExternalForm());
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
        } else {
            removeBtn.setVisible(false);
            String loadSentence = "Not found English word!!!";
            showMean.getEngine().loadContent("<h3 style='font-style: bold; text-align: center;margin-top:18px;color: red;'>" + loadSentence + "</h3>");
        }


    }

    /**
     * When user clicks on Translate button, app will translate word.
     *
     * @param event is click event.
     */
    @FXML
    public void clickTranslate(ActionEvent event) {
        String curWord = wordTranslate.getText();
        translate(curWord);
    }

    /**
     * When user  presses Enter on keyboard, app will translate word.
     *
     * @param keyEvent is key event.
     */
    public void submitTranslate(KeyEvent keyEvent) {
        String curWord = wordTranslate.getText();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            translate(curWord);
        }
    }

    /**
     * When user click exit button, the app will back to home page.
     *
     * @param mouseEvent is event mouse click.
     */
    public void goToHome(MouseEvent mouseEvent) {
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

    /**
     * When user enter the words to translate,
     * it will get all words is related to the characters user entered.
     *
     * @param keyEvent is the key event user.
     */

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


    /**
     * When user clicks on favorites list word, screen will show all words user likes it.
     *
     * @param event is the click event.
     */

    public void clickFavoriteListWord(ActionEvent event) {
        pronounceBtn.setVisible(false);
        favoriteBtn.setVisible(false);
        removeBtn.setVisible(false);
        showMean.getEngine().loadContent("");
        Set<Word> list = myDictionary.getFavoriteWord();
        ArrayList<String> specialList = new ArrayList<String>();
        for (Word li : list) {
            specialList.add(li.getTarget());
        }
        ObservableList<String> items = FXCollections.observableArrayList(specialList);
        listSpecialWord.setItems(items);
        listSpecialWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!listSpecialWord.getSelectionModel().isEmpty()) {
                    String selectedWord = (String) listSpecialWord.getSelectionModel().getSelectedItem();
                    wordTranslate.setText(selectedWord);
                    translate(selectedWord);
                }
            }
        });

    }

    /**
     * When the user looks up a word, the search history is saved.
     * When the user presses a button, the words they have looked up will be displayed.
     *
     * @param event
     */
    public void clickHistory(ActionEvent event) {
        pronounceBtn.setVisible(false);
        favoriteBtn.setVisible(false);
        showMean.getEngine().loadContent("");
        Set<Word> list = myDictionary.getHistorySearch();
        ArrayList<String> specialList = new ArrayList<String>();
        for (Word li : list) {
            specialList.add(li.getTarget());
        }
        ObservableList<String> items = FXCollections.observableArrayList(specialList);
        listSpecialWord.setItems(items);
        listSpecialWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!listSpecialWord.getSelectionModel().isEmpty()) {
                    String selectedWord = (String) listSpecialWord.getSelectionModel().getSelectedItem();
                    wordTranslate.setText(selectedWord);
                    translate(selectedWord);
                }
            }
        });
    }

    /**
     * When the user clicks on the star,
     * the word being searched will be added to favorites, and the star will turn yellow.
     *
     * @param event
     */
    public void clickLikeWord(ActionEvent event) {
        String text = wordTranslate.getText();
        Word tmpWord = new Word(text, null);
        Word curWord = myDictionary.lookupWord(tmpWord);
        boolean isFavoriteWord = myDictionary.checkIsFavoriteWord(curWord);
        if (!isFavoriteWord) {
            myDictionary.addFavoriteWord(curWord);
            Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/goldStar.jpg").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            favoriteBtn.setGraphic(imageView);
        } else {
            myDictionary.removeFavoriteWord(curWord);
            Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/grayStar.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            favoriteBtn.setGraphic(imageView);
        }
    }

    /**
     * The user click bin_button, screen will appear Warning Alert.
     * If user choose Ok button, word will be deleted.
     * @param mouseEvent is the mouse event user clicked.
     */
    public void removeWord(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa?");

        String cssFile = getClass().getResource("MainDictionary/RemoveWord.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssFile);


        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeNO = new ButtonType("NO");

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeNO);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOK) {
                String tex = wordTranslate.getText();
                Word tmpWord = new Word(tex);
                Word curWod = myDictionary.lookupWord(tmpWord);
                myDictionary.removeWord(curWod);
                translate(tex);
            } else if (response == buttonTypeNO) {
                System.out.println(" ");
            }
        });
    }
}