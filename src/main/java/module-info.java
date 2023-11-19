module com.example.dictionaryenvi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires java.sql;
    requires javafx.web;
    requires javafx.media;

    opens com.example.dictionaryenvi to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.Exercises.Dictation to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.Utils to javafx.fxml;
    opens com.example.dictionaryenvi.Exercise.ExerciseSelection to javafx.fxml;

    exports com.example.dictionaryenvi;
    exports com.example.dictionaryenvi.Exercise.Exercises.Dictation;
    exports com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;
    exports com.example.dictionaryenvi.Exercise.ExerciseSelection;

    exports com.backend.TopicWord.Utils to com.fasterxml.jackson.databind;
    exports com.backend.OnlineDictionary.OnlineDictionaries to com.fasterxml.jackson.databind;
    opens com.backend.OnlineDictionary.Utils to com.fasterxml.jackson.databind;

    exports com.backend.Exercise.Utils to com.fasterxml.jackson.databind;

    exports com.backend.Exercise.Exercises.MultipleChoice to com.fasterxml.jackson.databind;
    exports com.backend.Exercise.Exercises.Dictation to com.fasterxml.jackson.databind;

    opens com.backend.TopicWord.Utils to com.fasterxml.jackson.databind;
    opens com.backend.Exercise.Exercises.MultipleChoice to com.fasterxml.jackson.databind;
    exports com.backend.TopicWord.TopicWords.SimpleTopicWord to com.fasterxml.jackson.databind;
    opens com.backend.TopicWord.TopicWords.SimpleTopicWord to com.fasterxml.jackson.databind;
    exports com.backend.TopicWord.TopicWords.DetailedTopicWord to com.fasterxml.jackson.databind;
    opens com.backend.TopicWord.TopicWords.DetailedTopicWord to com.fasterxml.jackson.databind;

    exports com.example.dictionaryenvi.Exercise.ExerciseScene;
}
