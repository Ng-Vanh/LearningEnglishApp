package com.example.dictionaryenvi;

import com.backend.Connection.WordDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private WebView showMean;
    @FXML
    private TextField wordTranslate;
    @FXML
    private ListView suggestListWords;

    @FXML
    public void clickTranslate(ActionEvent event){
        String curWord = wordTranslate.getText();
        String s = WordDataAccess.getInstance().translateWord(curWord);
        if(s.equals("")){
            showMean.getEngine().loadContent("Not found!!!");
        }else{
            showMean.getEngine().loadContent(s);
        }
    }

    public void goToHome(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
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