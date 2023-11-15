package com.example.dictionaryenvi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader LoginPage = new FXMLLoader(Application.class.getResource("Login/Login.fxml"));

        Scene homeScene = new Scene(LoginPage.load(),960,576);
        stage.setTitle("Dictionary En-Vi");
        stage.setScene(homeScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}