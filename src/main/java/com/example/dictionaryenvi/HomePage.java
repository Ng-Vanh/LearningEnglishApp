package com.example.dictionaryenvi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.example.dictionaryenvi.Login.currentUser;

public class HomePage {
    @FXML
    private ImageView logOutBtn, userInfoBtn;

    public void goToDictionary(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDictionary/MainDictionary.fxml"));
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

    public void clickLogOut(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login/Login.fxml"));
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

    public void clickShowUserInfo(MouseEvent mouseEvent) {
        Image avatarImage = new Image(getClass().getResource("/com/example/dictionaryenvi/HomePage/image/userAvt.png").toExternalForm());
        ImageView avatarImageView = new ImageView(avatarImage);

        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
        Label fullNameLabel = new Label("Name: " + fullName);
        int score1 = currentUser.getScoreGame1();
        int score2 = currentUser.getScoreGame2();
        Label scoreLabel1 = new Label("Dictation: " + score1);
        Label scoreLabel2 = new Label("MultipleChoice: " + score2);

        VBox userInfoBox = new VBox(10);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.getChildren().addAll(avatarImageView, fullNameLabel, scoreLabel1, scoreLabel2);

        Dialog<Void> dialog = new Dialog<>();

        dialog.getDialogPane().setMinWidth(360);
        dialog.getDialogPane().setContent(userInfoBox);
        dialog.initStyle(StageStyle.UNDECORATED);

        String cssFile = getClass().getResource("HomePage/UserInfo.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(cssFile);


        // Tạo nút đóng dialog
        Button closeButton = new Button("");
        closeButton.setOnAction(event -> dialog.close());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    public void goToGame2(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercises/Dictation/FXML/Dictation.fxml"));
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

    public void goToGame1(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercises/MultipleChoice/FXML/MultipleChoice.fxml"));
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
}
