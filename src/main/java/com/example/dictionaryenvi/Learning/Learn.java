package com.example.dictionaryenvi.Learning;
import com.backend.Connection.LearnedDataAccess;
import com.backend.OnlineDictionary.Utils.Audio;
import com.backend.User.UserLearnWord;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.dictionaryenvi.TopicWord.currentUserLearnWord;
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
    @FXML
    private ListView listViewWord;
    @FXML
    private Pane listViewWordContainer;
    @FXML
    private Label topicTitle;
    private int id;
    private double currentRotateFront;
    private double currentRotateBack;
    private boolean flag;
    private boolean isShowListWord;
    private Map<String , Integer> hashMap = new HashMap<>();
    private LearnedDataAccess learnedDataAccess = new LearnedDataAccess();
    private List<Card> cardList = new ArrayList<Card>();
    private ArrayList<UserLearnWord> learnedListWord = new ArrayList<UserLearnWord>();
    private static final String EXCEL_FILE_PATH = "src/main/resources/com/example/dictionaryenvi/Learn/WordTopic.xlsx";
    public void initialize() {

        flag = false;
        isShowListWord = false;
        backFlashCard.setRotationAxis(Rotate.X_AXIS);
        currentRotateFront = 0;
        currentRotateBack = -90;
        id = 0;
        backFlashCard.setRotate(currentRotateBack);
        String currentTopic = currentUserLearnWord.getTopic();
        String currentUser = currentUserLearnWord.getUsername();
        learnedListWord = learnedDataAccess.getLearnedListWord(currentUser , currentTopic);
        topicTitle.setText(currentTopic);
        readExcelFIle(currentTopic);
        showCard();

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Card card : cardList) {
            items.add(card.getWord());
        }
        listViewWord.setItems(items);
        listViewWord.setCellFactory(param -> new ListCell<String>() {
            {
                setOnMouseClicked(event -> {
                    String selectedItem = (String)getItem();
                    id = hashMap.get(selectedItem) - 1;
                    showListWord();
                    showCard();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
            }
        });
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
            processLearnWord();
            updateLearnWordWhenClose();
        });
        starImg2.setOnMouseClicked(event -> {
            event.consume();
            processLearnWord();
            updateLearnWordWhenClose();
        });

    }
    public void updateLearnWordWhenClose() {
        Stage currentStage = (Stage) frontFlashCard.getScene().getWindow();
        currentStage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                updateLearnWordToDatabase();
            }
        });
    }
    public void updateLearnWordToDatabase() {
        List<UserLearnWord> newLearnWordList = new ArrayList<UserLearnWord>();
        for (Card card : cardList) {
            String wordStr = card.getWord();
            boolean isLearnWord = card.isLearnWord();
//            System.out.println(wordStr + " " + isLearnWord);
            if(!learnedListWord.contains(wordStr) && isLearnWord) {
                UserLearnWord newUserLearnWord = new UserLearnWord();
                newUserLearnWord.setUsername(currentUserLearnWord.getUsername());
                newUserLearnWord.setTopic(currentUserLearnWord.getTopic());
                newUserLearnWord.setWord(wordStr);
                newLearnWordList.add(newUserLearnWord);
            }
        }
//        for (UserLearnWord userLearnWord : newLearnWordList) {
//            System.out.println(userLearnWord.getUsername() + "  "
//                    + userLearnWord.getTopic() + "  "
//                    + userLearnWord.getWord());
//        }
        learnedDataAccess.insertAll(newLearnWordList);
        System.out.println("Learn Word has been updated");
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
                UserLearnWord newUserLearnWord = new UserLearnWord();
                newUserLearnWord.setUsername(currentUserLearnWord.getUsername());
                newUserLearnWord.setTopic(currentUserLearnWord.getTopic());
                newUserLearnWord.setWord(wordStr);
                boolean isLearnWord = learnedListWord.contains(newUserLearnWord);
                id += 1;
//                System.out.println(wordStr + " " + isLearnWord);
                Card card = new Card(wordStr , exampleStr , pronounceStr , explain , isLearnWord);
                cardList.add(card);
                hashMap.put(wordStr , id);
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
    public void showCard() {
        frontFlashCard.setEffect(new DropShadow(20 , BLACK));
        backFlashCard.setEffect(new DropShadow(20 , BLACK));
        listViewWordContainer.setEffect(new DropShadow(20 , BLACK));

        Card card = cardList.get(id);
        String wordStr = card.getWord();
        String exampleStr = card.getExample();
        String pronounceStr = card.getPronounce();
        String explainStr = card.getExplain();
        boolean isLearnWord = card.isLearnWord();
//        System.out.println(wordStr + " | " + exampleStr + " | " + pronounceStr + " | "
//                + explainStr  +  " | " + ((isLearnWord) ? "learn" : "haven't learn yet"));

        word.setText(wordStr);
        word1.setText(wordStr);
        example.setText(exampleStr);
        pronounce.setText(pronounceStr);
        explain.setText(explainStr);
        setImageStar(isLearnWord);

        Audio audio = new Audio(card.getWord());
        Media media = new Media(audio.getAudioLink());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void nextCard() {
        if(this.id < 29) {
            this.id += 1;
            showCard();
        }
    }

    public void playAudio() {
        Card card = cardList.get(id);
        String word = card.getWord();
        Audio audio = new Audio(word);
        Media media = new Media(audio.getAudioLink());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public void setImageStar(boolean isLearnWord) {
        String imageResource = "";
        if(isLearnWord) {
            imageResource = "/com/example/dictionaryenvi/Learn/Image/goldStar.jpg";
        }
        else {
            imageResource = "/com/example/dictionaryenvi/Learn/Image/grayStar.png";
        }
        Image image = new Image(getClass().getResource(imageResource).toExternalForm());
        ImageView imageView1 = new ImageView(image);
        ImageView imageView2 = new ImageView(image);
        starImg1.setImage(imageView1.getImage());
        starImg2.setImage(imageView2.getImage());
    }
    public void processLearnWord() {
        Card card = cardList.get(id);
        if(card.isLearnWord()) {
//            learnedDataAccess.delete(currentUserLearnWord);
            card.setLearnWord(false);
            cardList.set(id , card);
            setImageStar(false);
        }
        else {
//            learnedDataAccess.insert(currentUserLearnWord);
            card.setLearnWord(true);
            cardList.set(id , card);
            setImageStar(true);
        }
    }
    public void clickBackImg(MouseEvent mouseEvent) {
        updateLearnWordToDatabase();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/TopicWord/TopicWord.fxml"));
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

    public void showListWord() {
        if(isShowListWord) {
            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.1) , listViewWordContainer);
            slideIn.setToY(listViewWordContainer.getHeight());
            slideIn.setFromY(0);
            slideIn.play();
            slideIn.setOnFinished(e -> {
                listViewWordContainer.setVisible(false);
            });
            isShowListWord = false;
        }
        else {
            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.1) , listViewWordContainer);
            slideIn.setToY(0);
            slideIn.setFromY(listViewWordContainer.getHeight());
            slideIn.play();
            listViewWordContainer.setVisible(true);
            isShowListWord = true;
        }
    }

    public void clickKnowThisWord(MouseEvent mouseEvent) {
        Card card = cardList.get(id);
        if(!card.isLearnWord()) {
//            System.out.println(card.getWord());
            processLearnWord();
        }
        nextCard();
    }
}

