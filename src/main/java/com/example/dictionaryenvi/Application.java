package com.example.dictionaryenvi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        FXMLLoader homePage = new FXMLLoader(Application.class.getResource("homePage.fxml"));

        Scene homeScene = new Scene(homePage.load(),960,576);
        // Scene scene = new Scene(fxmlLoader.load(), 768, 576);
        stage.setTitle("Dictionary En-Vi");
        stage.setScene(homeScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}