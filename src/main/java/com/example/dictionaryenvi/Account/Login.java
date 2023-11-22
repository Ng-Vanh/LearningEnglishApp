package com.example.dictionaryenvi.Account;

import com.backend.User.User;
import com.example.dictionaryenvi.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends UserInformation {
    @FXML
    private Button signUpBtn;
    @FXML
    private Hyperlink forgotPasswordHpl;
    @FXML
    private Label loginFail1;
    @FXML
    private Label loginFail2;
    @FXML
    public AnchorPane mainPane;
    @FXML
    private Button loginBtn;
    @FXML
    private ImageView loadingGif;
    private boolean processingLogin;
    public static User currentUser;
    public void initialize() {
        username.setFocusTraversable(true);
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    clickLogin();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        password.setFocusTraversable(true);
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    clickLogin();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        loginBtn.setOnMouseClicked(event -> {
            try {
                clickLogin();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
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

    public void clickLogin() throws InterruptedException {
        if(processingLogin) return;
        processingLogin = true;
        loginBtn.setText("");
        loadingGif.setVisible(true);

        String usernameStr = username.getText();
        String passwordStr = password.getText();
        passwordStr = Hashing.hashWithSHA256(passwordStr);
        User user = new User(usernameStr, passwordStr);

//        isCorrectAccount = userDataAccess.isCorrectAccount(user);
        CheckLoginService checkLoginService = new CheckLoginService(user ,userDataAccess , usernameStr);
        String finalPasswordStr = passwordStr;
        checkLoginService.setOnSucceeded(event -> {
            loginBtn.setText("Login");
            processingLogin = false;
            loadingGif.setVisible(false);
            boolean isCorrectAccount = checkLoginService.getValue();
            if (isCorrectAccount && !usernameStr.isEmpty() && !finalPasswordStr.isEmpty()) {
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
                System.out.println(usernameStr + " " + finalPasswordStr);
            } else {
                username.setStyle("-fx-border-color: red; -fx-border-radius: 5");
                password.setStyle("-fx-border-color: red; -fx-border-radius: 5");
                loginFail1.setVisible(true);
                loginFail2.setVisible(true);
            }
        });
        checkLoginService.start();
    }

    public void goToSignUp(MouseEvent mouseEvent) {
        if(processingLogin) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Account/SignUp/FXML/SignUp.fxml"));
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

    public void goToResetPassword(MouseEvent mouseEvent) {
        if(processingLogin) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Account/ResetPassword/FXML/ResetPassword.fxml"));
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
