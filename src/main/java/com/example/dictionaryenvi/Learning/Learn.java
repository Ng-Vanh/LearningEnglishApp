package com.example.dictionaryenvi.Learning;
import com.backend.Connection.LearnedDataAccess;
import com.backend.OnlineDictionary.Utils.Audio;
import com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWord;
import com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader;
import com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWord;
import com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWordLoader;
import com.backend.User.User;
import com.backend.User.UserLearnWord;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
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
import java.text.SimpleDateFormat;
import java.util.*;

import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.getDetailedTopicWordMap;
import static com.example.dictionaryenvi.TopicWord.TopicWord.currentUserLearnWord;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.rgb;

public class Learn {
    @FXML
    public Label type;
    @FXML
    private Label word;
    @FXML
    private Label word1;
    @FXML
    private Label cloneWord;
    @FXML
    private Label example;
    @FXML
    private Label cloneExample;
    @FXML
    private Label pronounce;
    @FXML
    private Label explain;
    @FXML
    private Pane cloneFrontCard;
    @FXML
    private Pane frontFlashCard;
    @FXML
    private Pane backFlashCard;
    @FXML
    private ImageView pronounceImg1;
    @FXML
    private ImageView pronounceImg2;
    @FXML
    private ImageView clonePronounce;
    @FXML
    private ImageView starImg1;
    @FXML
    private ImageView starImg2;
    @FXML
    private ImageView cloneStar;
    @FXML
    private ListView listViewWord;
    @FXML
    private Pane listViewWordContainer;
    @FXML
    private Label topicTitle;
    private int id;
    private int sizeOfListTopic;
    private double currentRotateFront;
    private double currentRotateBack;
    private boolean flag;
    private boolean isShowListWord;
    private String currentTopicStr;
    private static String currentUsernameStr;
    private Map<String , Integer> hashMap = new HashMap<>();
    private static LearnedDataAccess learnedDataAccess = new LearnedDataAccess();
    private List<Card> cardList = new ArrayList<Card>();
    private static ArrayList<UserLearnWord> learnedListWord = new ArrayList<UserLearnWord>();
    public void initialize() {

        flag = false;
        isShowListWord = false;
        backFlashCard.setRotationAxis(Rotate.X_AXIS);
        currentRotateFront = 0;
        currentRotateBack = -90;
        id = 0;
        backFlashCard.setRotate(currentRotateBack);
        currentTopicStr = currentUserLearnWord.getTopic();
        currentUsernameStr = currentUserLearnWord.getUsername();
        learnedListWord = learnedDataAccess.getLearnedListWord(currentUsernameStr , currentTopicStr);
        topicTitle.setText(currentTopicStr);
//        currentTopicStr = "Random";
        if(currentTopicStr.equals("Random")) {
            getRandomDailyWord();
        }
        else getDataWord();
//        getRandomDailyWord();
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
//        ArrayList<SimpleTopicWord> test = userSimpleTopicWordList();
//        for (SimpleTopicWord simpleTopicWord : test) {
//            System.out.println(simpleTopicWord.getWord() + " " + simpleTopicWord.getTopic());
//        }
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
        if (newLearnWordList.isEmpty()) {
            return;
        }
        learnedDataAccess.insertAll(newLearnWordList);
        System.out.println("Learn Word has been updated");
    }


    public void getDataWord() {
        HashMap<String, ArrayList<DetailedTopicWord>> detailedTopicWordMap = getDetailedTopicWordMap();
        ArrayList<SimpleTopicWord> simpleTopicWordList = SimpleTopicWordLoader.getSimpleTopicWordList();
        ArrayList<DetailedTopicWord> detailedTopicWordList = DetailedTopicWordLoader.getDetailedTopicWordListFromSimpleTopicWordList(simpleTopicWordList);
        addWordListToCard(detailedTopicWordList);

    }
    public void getRandomDailyWord() {
        HashMap<String, ArrayList<DetailedTopicWord>> detailedTopicWordMap = getDetailedTopicWordMap();
        ArrayList<SimpleTopicWord> simpleTopicWordList = DetailedTopicWordLoader.globalFullSimpleTopicWordList;
        ArrayList<SimpleTopicWord> randomWordList = DailyRandomWordGenerator.generateDailyRandomWords(simpleTopicWordList);
//        for (SimpleTopicWord simpleTopicWord : randomWordList) {
//            System.out.println("Random : " + simpleTopicWord.getTopic() + " " + simpleTopicWord.getWord());
//        }
        ArrayList<DetailedTopicWord> detailedTopicWordList = DetailedTopicWordLoader.getDetailedTopicWordListFromSimpleTopicWordList(randomWordList);
        addWordListToCard(detailedTopicWordList);
    }
    public void addWordListToCard(ArrayList<DetailedTopicWord> detailedTopicWordList) {
        int currentId = 0;
        for (DetailedTopicWord detailedTopicWord : detailedTopicWordList) {
            String wordStr = detailedTopicWord.getDefinition().getWord();
            String topicStr = detailedTopicWord.getDefinition().getTopic();
            String typeStr = detailedTopicWord.getDefinition().getType();
            String exampleStr = detailedTopicWord.getDefinition().getExample();
            String pronounceStr = detailedTopicWord.getDefinition().getPhonetic();
            String explainStr = detailedTopicWord.getDefinition().getExplain();
            UserLearnWord newUserLearnWord = new UserLearnWord();
            newUserLearnWord.setUsername(currentUsernameStr);
            newUserLearnWord.setTopic(currentTopicStr);
            newUserLearnWord.setWord(wordStr);
            boolean isLearnWord = learnedListWord.contains(newUserLearnWord);
            if(topicStr.equals(currentTopicStr) || currentTopicStr.equals("Random")) {
                currentId += 1;
                System.out.println(currentId + " " + wordStr + " " + topicStr);
                Card card = new Card(wordStr , typeStr , exampleStr , pronounceStr , explainStr , isLearnWord);
                cardList.add(card);
                hashMap.put(wordStr , currentId);
            }
        }
        sizeOfListTopic = currentId;
    }
    public static ArrayList<SimpleTopicWord> userSimpleTopicWordList() {
        ArrayList<SimpleTopicWord> wordToMakeExerciseList = new ArrayList<SimpleTopicWord>();
        ArrayList<UserLearnWord> learnedListWordAllTopic = learnedDataAccess.getWordsFollowEachUser(currentUsernameStr);
        for (UserLearnWord userLearnWord : learnedListWordAllTopic) {
            SimpleTopicWord simpleTopicWord = new SimpleTopicWord(userLearnWord.getTopic() , userLearnWord.getWord());
            wordToMakeExerciseList.add(simpleTopicWord);
        }
        return wordToMakeExerciseList;
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
        cloneFrontCard.setEffect(new DropShadow(20 , BLACK));
        listViewWordContainer.setEffect(new DropShadow(20 , BLACK));

        Card card = cardList.get(id);
        String wordStr = card.getWord();
        String typeStr = card.getType();
        String exampleStr = card.getExample();
        String pronounceStr = card.getPronounce();
        String explainStr = card.getExplain();
        boolean isLearnWord = card.isLearnWord();
//        System.out.println(wordStr + " | " + exampleStr + " | " + pronounceStr + " | "
//                + explainStr  +  " | " + ((isLearnWord) ? "learn" : "haven't learn yet"));

        word.setText(wordStr);
        word1.setText(wordStr);
        type.setText(typeStr);
        example.setText(exampleStr);
        pronounce.setText(pronounceStr);
        explain.setText(explainStr);
        setImageStar(isLearnWord);

        Audio audio = new Audio(card.getWord());
        Media media = new Media(audio.getAudioLink());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void transitionCloneCard() {
        cloneFrontCard.setVisible(true);
        Card card = cardList.get(id);
        cloneWord.setText(card.getWord());
        cloneExample.setText(card.getExample());
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5));
        transition.setNode(cloneFrontCard);
        transition.setFromX(0);
        transition.setToX(-1000);
        transition.play();
    }
    public void nextCard() {
        if(this.id < sizeOfListTopic - 1) {
            transitionCloneCard();
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

