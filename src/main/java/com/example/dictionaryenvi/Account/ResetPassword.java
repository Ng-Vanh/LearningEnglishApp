package com.example.dictionaryenvi.Account;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ResetPassword extends UserInformation {
    @FXML
    private ImageView loadingGif;
    @FXML
    private Button resetPasswordBtn;
    @FXML
    private Label notification;
    @FXML
    private ImageView backImg;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private PasswordField confirmPassword;
    private boolean processingResetPassword;
    public void initialize() {
        username.setFocusTraversable(true);
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickResetPasswordBtn();
            }
        });
        password.setFocusTraversable(true);
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickResetPasswordBtn();
            }
        });
        confirmPassword.setFocusTraversable(true);
        confirmPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickResetPasswordBtn();
            }
        });
        resetPasswordBtn.setOnMouseClicked(event -> {
            clickResetPasswordBtn();
        });

    }
    public void clickResetPasswordBtn() {
        if(processingResetPassword) return;
        resetPasswordBtn.setText("");
        loadingGif.setVisible(true);
        processingResetPassword = true;

        String usernameStr = username.getText();
        String passwordStr = password.getText();
        passwordStr = Hashing.hashWithSHA256(passwordStr);
        String confirmPasswordStr = confirmPassword.getText();
        confirmPasswordStr = Hashing.hashWithSHA256(confirmPasswordStr);
        User user = new User(usernameStr , passwordStr);
//        boolean isExistingUser = userDataAccess.isExistingUser(usernameStr);
        boolean isConfirmEquals = passwordStr.equals(confirmPasswordStr);
        boolean isPasswordEmpty = (confirmPasswordStr.equals(Hashing.hashWithSHA256("")));
        boolean isUsernameEmpty = usernameStr.isEmpty();
        if(isUsernameEmpty) {
            username.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("Username cannot be empty!");
            notification.setVisible(true);
            resetPasswordBtn.setText("Reset PassWord");
            loadingGif.setVisible(false);
            processingResetPassword = false;
        }
        else if(!isConfirmEquals) {
            confirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("The password confirmation dose not match!");
            notification.setText("The password confirmation dose not match!");
            notification.setVisible(true);
            resetPasswordBtn.setText("Reset PassWord");
            loadingGif.setVisible(false);
            processingResetPassword = false;
        }
        else if(isPasswordEmpty) {
            confirmPassword.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
            notification.setText("Password cannot be empty!");
            notification.setText("Password cannot be empty!");
            notification.setVisible(true);
            resetPasswordBtn.setText("Reset PassWord");
            loadingGif.setVisible(false);
            processingResetPassword = false;
        }
        else {
            CheckExistingUserService checkExistingUserService = new CheckExistingUserService(user , userDataAccess , usernameStr);
            checkExistingUserService.setOnSucceeded(event -> {
                processingResetPassword = false;
                resetPasswordBtn.setText("Reset Password");
                loadingGif.setVisible(false);
                boolean isExistingUser = checkExistingUserService.getValue();
                if(!isExistingUser) {
                    username.setStyle("-fx-border-color: red; -fx-border-radius: 5");
                    notification.setText("This username does not exist!");
                    notification.setVisible(true);
                }
                else {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/dictionaryenvi/Account/Login/FXML/Login.fxml"));
                    try {
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) resetPasswordBtn.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            });
            checkExistingUserService.start();

        }
        username.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                username.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                notification.setVisible(false);
            }
        });
        confirmPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmPassword.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                if(!isConfirmEquals || isPasswordEmpty) notification.setVisible(false);
            }
        });
        password.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmPassword.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: null; -fx-border-radius: 5");
                if(!isConfirmEquals || isPasswordEmpty) notification.setVisible(false);
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

    public void clickBackImg(MouseEvent mouseEvent) {
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
}
