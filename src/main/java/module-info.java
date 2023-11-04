module com.example.dictionaryenvi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    opens com.example.dictionaryenvi to javafx.fxml;
    exports com.example.dictionaryenvi;
    exports com.example.dictionaryenvi.Exercises.MultipleChoice;
    opens com.example.dictionaryenvi.Exercises.MultipleChoice to javafx.fxml;
}