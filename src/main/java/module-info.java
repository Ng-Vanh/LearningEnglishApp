module com.example.dictionaryenvi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    opens com.example.dictionaryenvi to javafx.fxml;
    exports com.example.dictionaryenvi;
}