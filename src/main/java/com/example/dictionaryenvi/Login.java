package com.example.dictionaryenvi;

import com.backend.Connection.UserDataAccess;
import com.backend.User.User;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLOutput;

public class Login extends UserInformation {
    public static User currentUser;
    @FXML
    private Label loginFail1;
    @FXML
    private Label loginFail2;
    @FXML
    public AnchorPane mainPane;
    @FXML
    private Button loginBtn;
    public void initialize() {
        username.setFocusTraversable(true);
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickLogin();
            }
        });
        password.setFocusTraversable(true);
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickLogin();
            }
        });
        loginBtn.setOnMouseClicked(event -> {
            clickLogin();
        });
    }
    public void clickLogin() {
        String usernameStr = username.getText();
        String passwordStr = password.getText();
        User user = new User(usernameStr , passwordStr);
        boolean isCorrectAccount = userDataAccess.isCorrectAccount(user);
        if(isCorrectAccount && !usernameStr.isEmpty() && !passwordStr.isEmpty()) {
            currentUser = userDataAccess.getUserInfo(usernameStr);
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("HomePage/HomePage.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(usernameStr + " " + passwordStr);
        }
        else {
            username.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            loginFail1.setVisible(true);
            loginFail2.setVisible(true);
        }
        username.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                username.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                loginFail1.setVisible(false);
                loginFail2.setVisible(false);
            }
        });
        password.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                username.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                loginFail1.setVisible(false);
                loginFail2.setVisible(false);
            }
        });
        username.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                username.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
            }
        });
        username.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Đặt màu viền về giá trị mặc định khi không hover
                username.setStyle("-fx-border-color: null;");
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

    public void goToSignUp(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp/SignUp.fxml"));
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

    public void goToResetPassword(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetPassword/ResetPassword.fxml"));
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
