package com.backend.Exercise.Exercises.MultipleChoice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Options {
    @JsonProperty("A")
    private String optionA;

    @JsonProperty("B")
    private String optionB;

    @JsonProperty("C")
    private String optionC;

    @JsonProperty("D")
    private String optionD;

    public Options() {

    }

    public Options(String optionA, String optionB, String optionC, String optionD) {
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("A: ").append(getOptionA()).append("\n");
        result.append("B: ").append(getOptionB()).append("\n");
        result.append("C: ").append(getOptionC()).append("\n");
        result.append("D: ").append(getOptionD());
        return result.toString();
    }
}
