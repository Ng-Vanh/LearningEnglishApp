package com.backend.Exercise.Exercises.MultipleChoice;

import com.backend.Exercise.Exercises.Dictation.DictationDescription;
import com.backend.Exercise.Utils.Exercise;
import com.backend.TopicWord.Utils.Description;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("MultipleChoice")
public class MultipleChoiceDescription extends Description {
    @JsonProperty("type")
    private String type = "MultipleChoice";

    @JsonProperty("question")
    private String question;

    @JsonProperty("options")
    private Options options;

    @JsonProperty("correctAnswer")
    private String correctAnswer;

    @JsonProperty("explanation")
    private String explanation;

    public MultipleChoiceDescription() {
        super();
    }

    public MultipleChoiceDescription(String question, Options options, String correctAnswer, String explanation) {
        super();
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public MultipleChoiceDescription(Exercise exercise) {
        super();
        if (exercise instanceof MultipleChoice) {
            MultipleChoice multipleChoice = (MultipleChoice) exercise;
            this.question = multipleChoice.getQuestion();
            this.options = multipleChoice.getOptions();
            this.correctAnswer = multipleChoice.getCorrectAnswer();
            this.explanation = multipleChoice.getExplanation();
        }
    }

    public MultipleChoice getMultipleChoice(MultipleChoiceDescription multipleChoiceDescription) {
        return new MultipleChoice(question, options, correctAnswer, explanation);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "type: " + type + "\n"
                + "question: " + question + "\n"
                + "options: " + options + "\n"
                + "correctAnswer: " + correctAnswer + "\n"
                + "explanation: " + explanation;
    }
}
