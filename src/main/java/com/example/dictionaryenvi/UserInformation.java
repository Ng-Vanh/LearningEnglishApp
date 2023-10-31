package com.example.dictionaryenvi;

import com.backend.Connection.UserDataAccess;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserInformation {
    @FXML
    protected TextField firstname;
    @FXML
    protected TextField lastname;
    @FXML
    protected TextField username;
    @FXML
    protected PasswordField password;
    protected UserDataAccess userDataAccess = new UserDataAccess();
}
