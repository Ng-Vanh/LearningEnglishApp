package com.backend.TopicWord.Utils;

import com.backend.Exercise.Exercises.Dictation.DictationDescription;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoiceDescription;
import com.backend.Exercise.Utils.Exercise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Quiz {

    @JsonProperty("exerciseType")
    private String exerciseType;

    @JsonProperty("description")
    private Description description;

    @JsonIgnore
    private Exercise exercise;

    public Quiz() {

    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Exercise type: " + exerciseType + "\n"
                + exercise;
    }
}
