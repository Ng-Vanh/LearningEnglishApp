package com.example.dictionaryenvi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;

public class Login {
    @FXML
    private TextField username;
    @FXML
    private PasswordField userpassword;
    @FXML
    private Button loginBtn;
    public void clickLogin(ActionEvent actionEvent) {
        String userName = username.getText();
        String userPassword = userpassword.getText();
        System.out.println(userName + " " + userPassword);
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
