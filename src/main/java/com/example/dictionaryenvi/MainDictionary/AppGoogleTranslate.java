package com.example.dictionaryenvi.MainDictionary;

import com.backend.Connection.UserDataAccess;
import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;
import com.backend.User.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.example.dictionaryenvi.Account.Login.currentUser;
import static com.example.dictionaryenvi.HomePage.HomePage.*;

public class AppGoogleTranslate {

    @FXML
    private TextArea inputTranslate;
    @FXML
    private WebView showTranslate;
    @FXML
    private ImageView swapLanguageBtn,leftFlag, rightFlag, leftSpeaker, rightSpeaker;
    private MediaPlayer LmediaPlayer,RmediaPlayer;

    public static final String flagEnImg = "/com/example/dictionaryenvi/MainDictionary/image/eng.png";
    public static final String flagViImg = "/com/example/dictionaryenvi/MainDictionary/image/vietnam.png";
    public static boolean leftFlagIsEn;
    public GoogleTranslate googleTranslate = new GoogleTranslate();

    /**
     * Function init something in page Google Translate.
     */
    public void initialize(){
        Image flagEn = new Image(getClass().getResource(flagEnImg).toExternalForm());
        Image flagVi = new Image(getClass().getResource(flagViImg).toExternalForm());
        leftFlag.setImage(flagEn);
        leftFlagIsEn = true;
        rightFlag.setImage(flagVi);
    }

    /**
     * Function create text follow form HTML.
     */
    private String makeHTMLForm(String text){
        return "<p style = 'font-size:24px; font-family: sans-serif; text-color: #666666'>" + text + "</p>";
    }


    /**
     * Back to main dictionary.
     */
    public void goToMainDict(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/MainDictionary.fxml"));
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
     * Translates word/sentence when user presses space or enter.
     */
    public void enterWordEvent(KeyEvent keyEvent) {
//        if(keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {}
//        {
//            String text = inputTranslate.getText();
//            String meaning = null;
//            if(leftFlagIsEn){
//                meaning = googleTranslate.getTranslation(text,"en","vi");
//            }else{
//                meaning = googleTranslate.getTranslation(text,"vi","en");
//            }
//            String loading = makeHTMLForm(meaning);
//            showTranslate.getEngine().loadContent(loading);
//            leftSpeaker.setVisible(true);
//            rightSpeaker.setVisible(true);
//        }
        System.out.println(inputTranslate.getText());
    }

    /**
     * Swaps languages translate between Vietnamese and English.
     */
    public void clickSwapLang(MouseEvent mouseEvent) {
        String loading = null;
        String html = showTranslate.getEngine().executeScript("document.documentElement.outerHTML").toString();
        Document doc = Jsoup.parse(html);
        Element body = doc.body();
        String curRight = body.text();

        inputTranslate.setText(curRight);

        if(leftFlagIsEn){
            leftFlag.setImage(new Image(getClass().getResource(flagViImg).toExternalForm()));
            rightFlag.setImage(new Image(getClass().getResource(flagEnImg).toExternalForm()));
            leftFlagIsEn = false;
            loading = googleTranslate.getTranslation(curRight,"vi","en");
        }else{
            leftFlag.setImage(new Image(getClass().getResource(flagEnImg).toExternalForm()));
            rightFlag.setImage(new Image(getClass().getResource(flagViImg).toExternalForm()));
            leftFlagIsEn = true;
            loading = googleTranslate.getTranslation(curRight,"en","vi");
        }
        showTranslate.getEngine().loadContent(makeHTMLForm(loading));
    }

    /**
     * play audio of paragraphs user translate.
     */


    public void clickLeftSpeaker(MouseEvent mouseEvent) {
        String text = inputTranslate.getText();
        String audioLink = googleTranslate.getAudioLink(text);
        Media media = new Media(audioLink);

        if (LmediaPlayer != null) {
            if (LmediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                LmediaPlayer.stop();
                return;
            }
        }

        LmediaPlayer = new MediaPlayer(media);
        LmediaPlayer.play();
    }

    /**
     * play audio of paragraphs user translate.
     */
    public void clickRightSpeaker(MouseEvent mouseEvent) {
        String html = showTranslate.getEngine().executeScript("document.documentElement.outerHTML").toString();
        Document doc = Jsoup.parse(html);
        Element body = doc.body();
        String text = body.text();
        String audioLink = googleTranslate.getAudioLink(text);
        Media media = new Media(audioLink);

        if (RmediaPlayer != null) {
            if (RmediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                RmediaPlayer.stop();
                return;
            }
        }

        RmediaPlayer = new MediaPlayer(media);
        RmediaPlayer.play();
    }

    /**
     * Move to home page.
     */
    public void goToHome(MouseEvent mouseEvent) {
        moveToHomePageNavbar(mouseEvent);
    }

    /**
     * Move to learn word follow each topic.
     */
    public void goToLearn(MouseEvent mouseEvent) {
        moveToLearnTopicWordNavbar(mouseEvent);
    }

    /**
     * Move to home page.
     */
    public void goToGame(MouseEvent mouseEvent) {
        moveToExerciseNavbar(mouseEvent);
    }

    /**
     * Translate paragraph.
     */
    public void clickTranslate(MouseEvent mouseEvent) {
        String text = inputTranslate.getText();
        String meaning = null;
        if(leftFlagIsEn){
            meaning = googleTranslate.getTranslation(text,"en","vi");
        }else{
            meaning = googleTranslate.getTranslation(text,"vi","en");
        }
        String loading = makeHTMLForm(meaning);
        showTranslate.getEngine().loadContent(loading);
        leftSpeaker.setVisible(true);
        rightSpeaker.setVisible(true);
    }

    public void clickUserInfo(MouseEvent mouseEvent) {
        clickUserInfoNavbar(mouseEvent);
    }

    public void clickLearnWordOfDay(MouseEvent mouseEvent) {
        moveToLearnWordOfDayNavbar(mouseEvent);
    }
}
