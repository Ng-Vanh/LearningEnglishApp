module com.example.dictionaryenvi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires javafx.web;
    opens com.example.dictionaryenvi to javafx.fxml;
    exports com.example.dictionaryenvi;
}