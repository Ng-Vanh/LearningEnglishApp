package com.example.dictionaryenvi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
        System.out.println("logOut");
    }

    public void clickShowUserInfo(MouseEvent mouseEvent) {
        System.out.println("GetUserInfo");
    }
}
