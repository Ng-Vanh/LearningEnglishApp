package com.example.dictionaryenvi;

import com.backend.User.User;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ResetPassword extends UserInformation {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private PasswordField confirmPassword;
    public void clickResetPasswordBtn(MouseEvent mouseEvent) {
        String usernameStr = username.getText();
        String passwordStr = password.getText();
        String confirmPasswordStr = confirmPassword.getText();
        User user = new User(usernameStr , passwordStr);
        if(passwordStr.equals(confirmPasswordStr)) {
            userDataAccess.updateAccount(user);
            String state = "success";
            String title = "Success!";
            String content = "New Password updated successfully!";
            Notification notification = new Notification(state , title , content);
            notification.showNotification(mouseEvent , mainPane);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                FXMLLoader loader = new FXMLLoader(Application.class.getResource("Login/Login.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene =new Scene(root);
                    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            pause.play();
        }
        else {
            confirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            String state = "error";
            String title = "Error!";
            String content = "The password confirmation dose not match";
            Notification notification = new Notification(state , title , content);
            notification.showNotification(mouseEvent , mainPane);
            confirmPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    confirmPassword.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                    password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                }
            });
            password.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    confirmPassword.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                    password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                }
            });
            confirmPassword.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    confirmPassword.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
                }
            });
            confirmPassword.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Đặt màu viền về giá trị mặc định khi không hover
                    confirmPassword.setStyle("-fx-border-color: null;");
                }
            });
            password.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    password.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
                }
            });
            password.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Đặt màu viền về giá trị mặc định khi không hover
                    password.setStyle("-fx-border-color: null;");
                }
            });
        }
    }
}
