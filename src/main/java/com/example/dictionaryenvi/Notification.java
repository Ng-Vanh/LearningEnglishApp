package com.example.dictionaryenvi;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Notification {
    private boolean isHide;
    private String state;
    private String title;
    private String content;
    public Notification() {
    }
    public Notification(String state , String title) {
        this.state = state;
        this.title = title;
    }
    public Notification(String state , String title , String content) {
        this.state = state;
        this.title = title;
        this.content = content;
    }
    public void showNotification(MouseEvent mouseEvent , AnchorPane mainPane) {
        Stage notificationStage = new Stage();
        String imageResource = "";
        if(this.state.equals("success")) {
            imageResource = "/com/example/dictionaryenvi/Notification/success.jpg";
        }
        else if(this.state.equals("alert")) {
            imageResource = "/com/example/dictionaryenvi/Notification/alert.jpg";
        }
        else if(this.state.equals("error")) {
            imageResource = "/com/example/dictionaryenvi/Notification/error.jpg";
        }
        Image image = new Image(getClass().getResource(imageResource).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(550);

        Label labelTitle = new Label(this.title);
        labelTitle.setLayoutX(70);
        labelTitle.setLayoutY(20);
        labelTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 23");
        Label labelContent = new Label(this.content);
        labelContent.setStyle("-fx-font-size: 15");
        labelContent.setLayoutX(70);
        labelContent.setLayoutY(60);

        Image imageClose = new Image(getClass().getResource("/com/example/dictionaryenvi/Notification/close.png").toExternalForm());
        ImageView imageCloseView = new ImageView(imageClose);
        imageCloseView.setFitHeight(20);
        imageCloseView.setFitWidth(21);
        imageCloseView.setLayoutX(500);
        imageCloseView.setLayoutY(20);

        AnchorPane notificationLayout = new AnchorPane();
        notificationLayout.getChildren().addAll(imageView , labelTitle , labelContent , imageCloseView);
        Scene notificationScene = new Scene(notificationLayout , 550 , 100);
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.initStyle(StageStyle.UNDECORATED);
        notificationStage.setScene(notificationScene);
        notificationStage.getScene().getStylesheets().add(getClass().getResource("/com/example/dictionaryenvi/Notification/Notification.css").toExternalForm());
//        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//        double x = stage.getX() + (stage.getWidth() - notificationStage.getWidth()) / 2;
//        notificationStage.setX(x);
//        double y = stage.getY() + (stage.getHeight() - notificationStage.getHeight()) / 2;
//        notificationStage.setY(y);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            notificationStage.close();
            mainPane.setEffect(null);
        });
        notificationStage.show();
        GaussianBlur blur = new GaussianBlur(10);
        mainPane.setEffect(blur);
        imageCloseView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                notificationStage.close();
                mainPane.setEffect(null);
            }
        });
        pause.play();
        System.out.println("Notification has been displayed");
    }
}
