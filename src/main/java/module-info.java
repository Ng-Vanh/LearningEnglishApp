module com.example.dictionaryenvi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.dictionaryenvi to javafx.fxml;
    exports com.example.dictionaryenvi;
}