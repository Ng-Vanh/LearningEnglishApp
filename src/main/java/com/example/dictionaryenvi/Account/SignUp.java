package com.example.dictionaryenvi.Account;

import com.backend.Connection.UserDataAccess;
import com.backend.User.User;
import com.example.dictionaryenvi.Application;
import com.example.dictionaryenvi.UserInformation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SignUp extends UserInformation {
    @FXML
    private Button signUpBtn;
    @FXML
    private Label notification;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private PasswordField confirmPassword;
    public void initialize() {
        firstname.setFocusTraversable(true);
        firstname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickSignUp();
            }
        });
        lastname.setFocusTraversable(true);
        lastname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickSignUp();
            }
        });
        username.setFocusTraversable(true);
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickSignUp();
            }
        });
        password.setFocusTraversable(true);
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickSignUp();
            }
        });
        confirmPassword.setFocusTraversable(true);
        confirmPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickSignUp();
            }
        });
        signUpBtn.setOnMouseClicked(event -> {
            clickSignUp();
        });
    }
    public void backToLogIn(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Account/Login/FXML/Login.fxml"));
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

    public void clickSignUp() {
        String passwordStr = password.getText();
        passwordStr = Hashing.hashWithSHA256(passwordStr);

        String confirmPasswordStr = confirmPassword.getText();
        confirmPasswordStr = Hashing.hashWithSHA256(confirmPasswordStr);

        String firstnameStr = firstname.getText();
        String lastnameStr = lastname.getText();
        String usernameStr = username.getText();
        User newUser = new User(firstnameStr , lastnameStr , usernameStr , passwordStr);
        boolean isExistingUser = userDataAccess.isExistingUser(usernameStr);
        boolean isConfirmEqual = passwordStr.equals(confirmPasswordStr);
        boolean isPasswordEmpty = passwordStr.isEmpty();
        boolean isUsernameEmpty = usernameStr.isEmpty();
        if(isUsernameEmpty) {
            username.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("Username cannot be empty!");
            notification.setVisible(true);
        }
        else if(isExistingUser) {
            username.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("Username already exists!");
            notification.setVisible(true);
        }
        else if(!isConfirmEqual) {
            confirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("The password confirmation dose not match");
            notification.setVisible(true);
        }
        else if(isPasswordEmpty) {
            confirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("Password cannot be empty!");
            notification.setVisible(true);
        }
        else {
            userDataAccess.insert(newUser);
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/dictionaryenvi/Account/Login/FXML/Login.fxml"));
            try {
                Parent root = loader.load();
                Scene scene =new Scene(root);
                Stage stage = (Stage) signUpBtn.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        username.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                username.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                if(isExistingUser) notification.setVisible(false);
            }
        });
        confirmPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmPassword.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                if(!isConfirmEqual) notification.setVisible(false);
            }
        });
        password.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmPassword.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                if(!isConfirmEqual) notification.setVisible(false);
            }
        });
        username.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                username.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
            }
        });
        confirmPassword.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmPassword.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
            }
        });
        password.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                password.setStyle("-fx-border-color: black; -fx-border-radius: 5;");
            }
        });
        username.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Đặt màu viền về giá trị mặc định khi không hover
                username.setStyle("-fx-border-color: null;");
            }
        });
        confirmPassword.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Đặt màu viền về giá trị mặc định khi không hover
                confirmPassword.setStyle("-fx-border-color: null;");
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
