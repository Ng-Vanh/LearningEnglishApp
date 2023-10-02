package com.example.dictionaryenvi;

import com.backend.Connection.WordDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class Controller {
    @FXML
    private WebView showMean;
    @FXML
    private TextField wordTranslate;

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
}