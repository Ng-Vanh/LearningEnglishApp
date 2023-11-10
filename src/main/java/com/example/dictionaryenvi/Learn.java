package com.example.dictionaryenvi;
import com.backend.OnlineDictionary.Utils.Audio;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.rgb;

public class Learn {
    @FXML
    private Label word;
    @FXML
    private Label word1;
    @FXML
    private Label example;
    @FXML
    private Label pronounce;
    @FXML
    private Label explain;
    @FXML
    private Pane frontFlashCard;
    @FXML
    private Pane backFlashCard;
    @FXML
    private ImageView pronounceImg1;
    @FXML
    private ImageView pronounceImg2;
    @FXML
    private ImageView starImg1;
    @FXML
    private ImageView starImg2;
    private int id;
    private double currentRotateFront;
    private double currentRotateBack;
    private boolean flag;
    private List<Card> cardList = new ArrayList<Card>();
    private static final String EXCEL_FILE_PATH = "src/main/resources/com/example/dictionaryenvi/Learn/WordTopic.xlsx";
    public void initialize() {
        flag = false;
        backFlashCard.setRotationAxis(Rotate.X_AXIS);
        currentRotateFront = 0;
        currentRotateBack = -90;
        id = 0;
        backFlashCard.setRotate(currentRotateBack);
        String currentTopic = "Animal";
        readExcelFIle(currentTopic);
        showCard(this.id);
        pronounceImg1.setOnMouseClicked(event -> {
            event.consume();
            playAudio();
        });
        pronounceImg2.setOnMouseClicked(event -> {
            event.consume();
            playAudio();
        });
        starImg1.setOnMouseClicked(event -> {
            event.consume();
            addLearnWord();
        });
        starImg2.setOnMouseClicked(event -> {
            event.consume();
            addLearnWord();
        });
    }
    public void readExcelFIle(String sheetName) {
        try {
            FileInputStream file = new FileInputStream(EXCEL_FILE_PATH);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);
            int id = 0;
            for (Row row : sheet) {
                String wordStr = row.getCell(0).toString();
                String exampleStr = row.getCell(1).toString();
                String pronounceStr = row.getCell(2).toString();
                String explain = row.getCell(3).toString();
                boolean isLearnWord = false;
                id += 1;
                Card card = new Card(wordStr , exampleStr , pronounceStr , explain , isLearnWord);
                cardList.add(card);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ParallelTransition createFrontCardFlipAnimation() {
        double newRotateFront = currentRotateFront + 90;
        RotateTransition flipFront = createFlip(this.frontFlashCard , currentRotateFront , newRotateFront);
        if(newRotateFront == 90)    currentRotateFront = -90;
        else currentRotateFront = newRotateFront;
        ParallelTransition flipCard = new ParallelTransition(flipFront);
        return flipCard;
    }
    public ParallelTransition createBackCardFlipAnimation() {
        double newRotateBack = currentRotateBack + 90;
        RotateTransition flipBack = createFlip(this.backFlashCard , currentRotateBack , newRotateBack);
        if(newRotateBack == 90)    currentRotateBack = -90;
        else currentRotateBack = newRotateBack;
        ParallelTransition flipCard = new ParallelTransition(flipBack);
        return flipCard;
    }
    public RotateTransition createFlip(Pane pane , double fromAngle , double toAngle) {
        RotateTransition flip = new RotateTransition(Duration.millis(500) , pane);
        flip.setAxis(Rotate.Y_AXIS);
        flip.setFromAngle(fromAngle);
        flip.setToAngle(toAngle);
        return flip;
    }
    public void flipFlashCard() {
        ParallelTransition flipFrontCard = createFrontCardFlipAnimation();
        ParallelTransition flipBackCard = createBackCardFlipAnimation();
        if(!flag) {
            flipFrontCard.setOnFinished(event -> {
                flipBackCard.play();
            });
            flipFrontCard.play();
        }
        else {
            flipBackCard.setOnFinished(event -> {
                flipFrontCard.play();
            });
            flipBackCard.play();
        }
        flag = !flag;
//        System.out.println(currentRotateFront + "  "  + currentRotateBack);
    }
    public void showCard(int id) {
        frontFlashCard.setEffect(new DropShadow(20 , BLACK));
        backFlashCard.setEffect(new DropShadow(20 , BLACK));
        Card card = cardList.get(id);
        System.out.println(card.getWord() + " | " + card.getExplain() + " | " + card.getPronounce());
        word.setText(card.getWord());
        word1.setText(card.getWord());
        example.setText(card.getExample());
        pronounce.setText(card.getPronounce());
        explain.setText(card.getExplain());
        Audio audio = new Audio(card.getWord());
        Media media = new Media(audio.getAudioLink());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void nextCard(MouseEvent mouseEvent) {
        this.id += 1;
        showCard(id);
    }

    public void playAudio() {
        Card card = cardList.get(id);
        String word = card.getWord();
        Audio audio = new Audio(word);
        Media media = new Media(audio.getAudioLink());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public void addLearnWord() {

    }
}

