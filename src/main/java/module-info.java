module com.example.dictionaryenvi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires javafx.web;
    requires javafx.media;
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.controlsfx.controls;

    opens com.example.dictionaryenvi to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.Exercises.Dictation to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.Utils to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.ExerciseSelection to javafx.fxml;
    opens com.backend.User to javafx.base;

    exports com.example.dictionaryenvi;
    exports com.example.dictionaryenvi.Exercise.Exercises.Dictation;
    exports com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;
    exports com.example.dictionaryenvi.Exercise.ExerciseSelection;
}
