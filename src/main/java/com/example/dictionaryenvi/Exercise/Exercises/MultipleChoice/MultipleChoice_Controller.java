package com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;

import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.stage.StageStyle;

public class MultipleChoice_Controller extends Exercise_Controller<MultipleChoice> {
    @FXML
    private ToggleButton optionA;

    @FXML
    private ToggleButton optionB;

    @FXML
    private ToggleButton optionC;

    @FXML
    private ToggleButton optionD;

    private MultipleChoice exercise;

    @Override
    protected void loadExerciseFromBank() {
        exerciseList = MultipleChoice.loadFromBank();
    }

    public void setQuestion(String question, String optionA, String optionB, String optionC, String optionD) {
        this.question.setText(question);
        this.optionA.setText("A. " + optionA);
        this.optionB.setText("B. " + optionB);
        this.optionC.setText("C. " + optionC);
        this.optionD.setText("D. " + optionD);

        this.optionA.setSelected(false);
        this.optionB.setSelected(false);
        this.optionC.setSelected(false);
        this.optionD.setSelected(false);
    }

    private void setQuestion(MultipleChoice multipleChoice) {
        String question = multipleChoice.getQuestion();
        String optionA = multipleChoice.getOptionA();
        String optionB = multipleChoice.getOptionB();
        String optionC = multipleChoice.getOptionC();
        String optionD = multipleChoice.getOptionD();

        setQuestion(question, optionA, optionB, optionC, optionD);

        this.exercise = multipleChoice;
    }

    @Override
    protected void generateQuestion() {
        setQuestion(exerciseList.get(questionIndex));
        setScoreLabel();
        setQuestionIndexLabel();
    }

    @Override
    protected String getUserAnswer() {
        if (optionA.isSelected()) {
            return "A";
        } else if (optionB.isSelected()) {
            return "B";
        } else if (optionC.isSelected()) {
            return "C";
        } else if (optionD.isSelected()) {
            return "D";
        } else {
            return null;
        }
    }

    @Override
    @FXML
    protected void submitAnswer() {
        String userAnswer = getUserAnswer();
        if (userAnswer != null) {
            if (exercise.isCorrect(userAnswer)) {
                score += 1;
//                setScoreLabel();
                System.out.println("Correct!");
                showAlert("Correct!", "Congrats, you got a new point!", true);

            } else {
                System.out.println("Incorrect, the correct answer is " + exercise.getCorrectAnswer() + ".");
                showAlert("Incorrect", "Sorry, the correct answer is " + exercise.getCorrectAnswer() + ".", false);
            }
            questionIndex += 1;
            generateQuestion();
//            setQuestionIndexLabel();
        } else {
            System.out.println("Please select an answer");
        }
    }

//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content + "\n" + multipleChoice.getExplanation());
//        alert.setHeight(200);
//        alert.showAndWait();
//    }

//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//
//        // Create a VBox to hold the content
//        VBox vbox = new VBox();
//        vbox.getChildren().addAll(new Label(content), new Label(formatExplanation()));
//
//
//        // Set the content to the custom VBox
//        alert.getDialogPane().setContent(vbox);
//        alert.initStyle(StageStyle.TRANSPARENT);
//
//        // Show the alert and wait for user interaction
//        alert.showAndWait();
//    }

//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//
//        // Create a VBox to hold the content
//        VBox vbox = new VBox();
//        vbox.getChildren().addAll(new Label(content), new Label(formatExplanation()));
//
//        // Remove the default content
//        alert.getDialogPane().getChildren().clear();
//
//        // Add the VBox as the new content
//        alert.getDialogPane().getChildren().add(vbox);
//
//        // Remove the close button
//        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//        alert.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);
//
//        // Set the alert as draggable
//        makeAlertDraggable(alert);
//
//        // Set a transparent style for the stage
//        alert.initStyle(StageStyle.TRANSPARENT);
//
//        // Show the alert and wait for user interaction
//        alert.showAndWait();
//    }


    @Override
    protected void showAlert(String title, String content, boolean isCorrect) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content + "\n\n" + exercise.getExplanation());

        // Remove the close button
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);

        // Set the alert as draggable
        makeAlertDraggable(alert);

        // Set background color based on correctness
        if (isCorrect) {
            alert.getDialogPane().getStyleClass().add("correct-alert");
        } else {
            alert.getDialogPane().getStyleClass().add("incorrect-alert");
        }

        // Set a style class for the OK button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("ok-button");

        // Load updated CSS file
        String cssFile = getClass().getResource("/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/CSS/Alert.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssFile);

        alert.setWidth(550);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Show the alert and wait for user interaction
        alert.showAndWait();
    }
}
