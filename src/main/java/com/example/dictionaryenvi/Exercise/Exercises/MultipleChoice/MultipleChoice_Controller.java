package com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;

import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.backend.Exercise.Utils.ExerciseLoader.getMultipleChoiceListFromSimpleTopicWordList;
import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.globalFullSimpleTopicWordList;
import static com.example.dictionaryenvi.Exercise.ExerciseScene.ExerciseScene_Controller.*;


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

    private ToggleGroup optionsGroup = new ToggleGroup();

    @Override
    protected Stage getStage() {
        return (Stage) optionA.getScene().getWindow();
    }

    @Override
    protected void loadExerciseFromBank() {
//        exerciseList = MultipleChoice.loadFromBank();
        exerciseList = getMultipleChoiceListFromSimpleTopicWordList(globalFullSimpleTopicWordList);
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

    @Override
    protected void setQuestion(MultipleChoice multipleChoice) {
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
        timerManager.startTimer();
//        setQuestion(exerciseList.get(questionIndex));
        setQuestion((MultipleChoice) currentExercise);
        setScoreLabel();
        setQuestionIndexLabel();

        this.optionA.setToggleGroup(optionsGroup);
        this.optionB.setToggleGroup(optionsGroup);
        this.optionC.setToggleGroup(optionsGroup);
        this.optionD.setToggleGroup(optionsGroup);

        resetButtonColor(optionA);
        resetButtonColor(optionB);
        resetButtonColor(optionC);
        resetButtonColor(optionD);
    }

    private ToggleButton getSelectedButton() {
        // Get the selected button from the ToggleGroup
        return (ToggleButton) optionsGroup.getSelectedToggle();
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void resetButtonColor(ToggleButton button) {
        button.setStyle(""); // This resets the style to the default state
    }

    private void setButtonColor(ToggleButton button, Color color) {
        // Change the text fill color of the button
        button.setStyle("-fx-background-color: " + toRGBCode(color) + ";");
    }

    @Override
    protected String getUserAnswer() {
        ToggleButton selectedButton = getSelectedButton();
        return (selectedButton != null) ? String.valueOf(selectedButton.getText().charAt(0)) : null;
    }

    @Override
    @FXML
    protected void submitAnswer() {
        String userAnswer = getUserAnswer();
        timerManager.stopTimer();
        if (userAnswer != null) {
            ToggleButton selectedButton = getSelectedButton();
            System.out.println(exercise);
            if (exercise.isCorrect(userAnswer)) {
                playCorrectEffect();
                globalScore += 1;
                setButtonColor(selectedButton, Color.GREEN);
                System.out.println("Correct!");
                showAlert("Correct!", "Congrats, you got a new point!", true);
            } else {
                playIncorrectEffect();
                setButtonColor(selectedButton, Color.RED);
                System.out.println("Incorrect, the correct answer is " + exercise.getCorrectAnswer() + ".");
                showAlert("Incorrect", "Sorry, the correct answer is " + exercise.getCorrectAnswer() + ".", false);
            }
            generateQuestion();
            selectedButton.getParent().requestFocus();
        } else {
            System.out.println("Please select an answer");
        }

        Stage stage = getStage();
        stage.close();
    }

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

    @Override
    protected void handleTimeout() {
        System.out.println("TIMEOUT!!");
    }

}
