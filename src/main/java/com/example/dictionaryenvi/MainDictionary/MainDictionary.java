package com.example.dictionaryenvi.MainDictionary;

import com.backend.Connection.FavoriteDataAccess;
import com.backend.Connection.HistoryDataAccess;
import com.backend.LocalDictionary.Dictionary.DictionaryManagement;
import com.backend.LocalDictionary.Dictionary.Word;
import com.backend.OnlineDictionary.Utils.Audio;
import com.backend.User.UserStatus;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.dictionaryenvi.Account.Login.currentUser;
import static com.example.dictionaryenvi.HomePage.HomePage.*;

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
    private ImageView removeBtn, addNewWordBtn, updateWordBtn;


    private static final DictionaryManagement myDictionary = new DictionaryManagement();
    private HistoryDataAccess historyDataAccess = new HistoryDataAccess();
    private FavoriteDataAccess favoriteDataAccess = new FavoriteDataAccess();


    /**
     * The function format word to the first letter is Upper Case,
     * and other characters is upper Case.
     *
     * @param str is input string.
     * @return the word is formatted according.
     */
    private String formatWord(String str) {
        String firstLetter = str.substring(0, 1).toUpperCase();
        String lastLetters = str.substring(1).toLowerCase();
        return firstLetter + lastLetters;
    }

    /**
     * The function translate word.
     *
     * @param curWord is the current word translated.
     */
    private void translate(String curWord) {
        favoriteBtn.setVisible(true);
        removeBtn.setVisible(true);
        updateWordBtn.setVisible(true);
        if (!curWord.isEmpty()) {
            String lowerCaseWord = curWord.toLowerCase();
            String titleWord = formatWord(curWord);
            Word tmpWord = new Word(lowerCaseWord, null);
            Word tmpMeaning = myDictionary.lookupWord(tmpWord);
            //boolean isFavoriteWord = myDictionary.checkIsFavoriteWord(tmpMeaning);
            UserStatus us = new UserStatus(currentUser.getUsername(),lowerCaseWord);
            boolean isFavoriteWord = favoriteDataAccess.isFavoriteWord(us);
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
            String meaningShow = "<h3 style = 'font-style: italic !important; color:green;'" + meaning + "</h3>";


            String showStr = "<h2 style ='color: red; font-style: italic; margin-left:32px;'>" + titleWord + "</h2>" + meaningShow;

            if (meaning.equals("not found!")) {
                favoriteBtn.setVisible(false);
                pronounceBtn.setVisible(false);
                removeBtn.setVisible(false);
                updateWordBtn.setVisible(false);
                String loadSentence = '"' + titleWord + '"' + " is not found!!!";
                showMean.getEngine().loadContent("<h3 style='font-style: bold; text-align: center;margin-top:18px;color: red;'>" + loadSentence + "</h3>");
            } else {
                // myDictionary.addHistorySearch(tmpMeaning);
                UserStatus userStatus = new UserStatus(currentUser.getUsername(), lowerCaseWord);
                historyDataAccess.insert(userStatus);
                showMean.getEngine().loadContent(showStr);
                Audio audioTranslation = new Audio(lowerCaseWord);
                System.out.println(audioTranslation.getAudioLink());
                pronounceBtn.setVisible(true);
                Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/speaker.png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(45);
                imageView.setFitHeight(45);
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
            pronounceBtn.setVisible(false);
            favoriteBtn.setVisible(false);
            removeBtn.setVisible(false);
            updateWordBtn.setVisible(false);
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
        moveToHomePageNavbar(mouseEvent);
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
        updateWordBtn.setVisible(false);
        showMean.getEngine().loadContent("");
//        Set<Word> list = myDictionary.getFavoriteWord();
//        ArrayList<String> specialList = new ArrayList<String>();
//        for (Word li : list) {
//            specialList.add(li.getTarget());
//        }
        List<String> specialList = favoriteDataAccess.getFavoriteWords(currentUser.getUsername());
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
     * @param event is the event user clicked History button.
     */
    public void clickHistory(ActionEvent event) {
        pronounceBtn.setVisible(false);
        favoriteBtn.setVisible(false);
        removeBtn.setVisible(false);
        updateWordBtn.setVisible(false);
        showMean.getEngine().loadContent("");
//        Set<Word> list = myDictionary.getHistorySearch();
//        ArrayList<String> specialList = new ArrayList<String>();
//        for (Word li : list) {
//            specialList.add(li.getTarget());
//        }
        ArrayList<String> specialList = (ArrayList<String>) historyDataAccess.getHistory(currentUser.getUsername());
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
     * @param event is the event user clicked on Favorite button.
     */
    public void clickLikeWord(ActionEvent event) {
        String text = wordTranslate.getText();
        Word tmpWord = new Word(text, null);
        Word curWord = myDictionary.lookupWord(tmpWord);
        //boolean isFavoriteWord = myDictionary.checkIsFavoriteWord(curWord);
        UserStatus us = new UserStatus(currentUser.getUsername(),text.toLowerCase());
        boolean isFavoriteWord = favoriteDataAccess.isFavoriteWord(us);
        if (!isFavoriteWord) {
            // myDictionary.addFavoriteWord(curWord);
            favoriteDataAccess.insert(us);
            Image image = new Image(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/image/goldStar.jpg").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            favoriteBtn.setGraphic(imageView);
        } else {
            //  myDictionary.removeFavoriteWord(curWord);
            favoriteDataAccess.delete(us);
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
     *
     * @param mouseEvent is the mouse event user clicked.
     */
    public void removeWord(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!!!");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa?");

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), alert.getDialogPane());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();


        String cssFile = getClass().getResource("/com/example/dictionaryenvi/MainDictionary/CSS/RemoveWord.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssFile);


        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeNO = new ButtonType("NO", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeNO);


        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOK) {
                String tex = wordTranslate.getText();
                Word tmpWord = new Word(tex);
                Word curWod = myDictionary.lookupWord(tmpWord);
                myDictionary.removeWord(curWod);
                translate(tex);
            } else if (response == buttonTypeNO|| response == ButtonType.CLOSE) {
                alert.close();
            }
        });

    }

    /**
     * The function add NewWord to Dictionary.
     * @param mouseEvent is the mouse user clicked AddButton.
     */
    public void clickAddNewWord(MouseEvent mouseEvent) {
        TextField wordTextField = new TextField();
        TextField definitionTextField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordTextField, 1, 0);
        grid.add(new Label("Meaning:"), 0, 1);
        grid.add(definitionTextField, 1, 1);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(grid);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add New Word");
        alert.setHeaderText("Please enter a new word and its meaning:");
        alert.setDialogPane(dialogPane);

        ButtonType buttonTypeOK = new ButtonType("OK", ButtonType.OK.getButtonData());
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), alert.getDialogPane());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        String cssFile = getClass().getResource("/com/example/dictionaryenvi/MainDictionary/CSS/AddNewWord.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssFile);

        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeOK) {
                String newWord = wordTextField.getText();
                String definition = definitionTextField.getText();

                String wordMeaing = "<h3>" + definition + "</h3>";
                Word word = new Word(newWord, wordMeaing);
                myDictionary.insertWord(word);
            }
        });
    }

    /**
     * Whuen user want to change word, user will click to here.
     * @param mouseEvent is the mouse event.
     */
    public void clickUpdateWord(MouseEvent mouseEvent) {
        String textEn = wordTranslate.getText();
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Update Word");
        dialog.setHeaderText("Vui lòng nhập nghĩa mới của từ " + '"' + textEn + '"');
        dialog.setContentText("Meaning:");

        String cssFile = getClass().getResource("/com/example/dictionaryenvi/MainDictionary/CSS/UpdateWord.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(cssFile);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), dialog.getDialogPane());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        dialog.showAndWait().ifPresent(word -> {
            if (!word.isEmpty()) {
                String meaningWord = "<h3>" + word + "</h3>";
                Word newWord = new Word(textEn, meaningWord);
                myDictionary.updateWord(newWord);
                System.out.println("Entered word: " + word);
            }
        });
        translate(textEn);
    }

    /**
     * Moves to page Google Translate.
     */
    public void goToGoogleTranslate(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/AppGoogleTranslate.fxml"));
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

    public void goToMainDict(MouseEvent mouseEvent) {
        moveToHomePageNavbar(mouseEvent);
    }

    public void goToGame(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/ExerciseScene/FXML/ExerciseScene.fxml"));
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

    public void goToLearn(MouseEvent mouseEvent) {
        moveToLearnTopicWordNavbar(mouseEvent);
    }

    public void clickUserInfo(MouseEvent mouseEvent) {
        clickUserInfoNavbar(mouseEvent);
    }

    public void clickLearnWordOfDay(MouseEvent mouseEvent) {
        moveToLearnWordOfDayNavbar(mouseEvent);
    }

    public void clickEdict(MouseEvent mouseEvent) {
        moveToDictionaryNavbar(mouseEvent);
    }
}