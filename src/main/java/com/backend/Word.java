package com.backend;

public class Word {
    private String target;
    private String explain;
    public Word() {
        this.target = "";
        this.explain = "";
    }
    /**
     * Constructs a new instance of the Word class.
     * @param target The English word.
     * @param explain The Vietnamese translation of the word.
     */
    public Word(String target, String explain) {
        this.target = target;
        this.explain = explain;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
