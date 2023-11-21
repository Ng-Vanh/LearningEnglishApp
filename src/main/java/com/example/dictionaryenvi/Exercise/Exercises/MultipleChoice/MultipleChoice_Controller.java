package com.example.dictionaryenvi.Exercise.Exercises.MultipleChoice;

import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;

import com.example.dictionaryenvi.Exercise.Utils.Exercise_Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    public void extraInit() {
        String cssFile = getClass().getResource("/com/example/dictionaryenvi/Exercise/Exercises/MultipleChoice/CSS/Button.css").toExternalForm();
        optionA.getStylesheets().add(cssFile);
        optionB.getStylesheets().add(cssFile);
        optionC.getStylesheets().add(cssFile);
        optionD.getStylesheets().add(cssFile);

        optionA.setOnMouseEntered(event -> {
            playHover();
        });

        optionB.setOnMouseEntered(event -> {
            playHover();
        });

        optionC.setOnMouseEntered(event -> {
            playHover();
        });

        optionD.setOnMouseEntered(event -> {
            playHover();
        });

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
        setQuestion(globalCurrentMultipleChoice);
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

    public void updateQuestion() {
        generateQuestion();
        globalShowingMultipleChoice = true;
        handleScene();
    }

    private ToggleButton getSelectedButton() {
        // Get the selected button from the ToggleGroup
        return (ToggleButton) optionsGroup.getSelectedToggle();
    }

    private void resetButtonColor(ToggleButton button) {
        button.setStyle(""); // This resets the style to the default state
    }

    private void setButtonColor(ToggleButton button, Color color) {
        String style = "-fx-background-color: " + toRGBCode(color) + ";";
        style += "-fx-effect: dropshadow(gaussian, " + toRGBCode(color) + ", 10, 0, 0, 0);";

        button.setStyle(style);
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
                showAlert(true, exercise.getCorrectAnswer(), exercise.getExplanation());
            } else {
                playIncorrectEffect();
                setButtonColor(selectedButton, Color.RED);
                System.out.println("Incorrect, the correct answer is " + exercise.getCorrectAnswer() + ".");
                showAlert(false, exercise.getCorrectAnswer(), exercise.getExplanation());
            }
            generateQuestion();
            selectedButton.getParent().requestFocus();
        } else {
            System.out.println("Please select an answer");
        }

        globalShowingMultipleChoice = false;
        handleScene();
        System.out.println("CLOSING MULTIPLE CHOICE");
    }
}