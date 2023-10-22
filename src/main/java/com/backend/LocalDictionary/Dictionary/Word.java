package com.backend.LocalDictionary.Dictionary;

import java.util.Objects;

public class Word {
    private String target;
    private String explain;
    private boolean isFavoriteWord;
    public Word() {
        this.target = "";
        this.explain = "";
    }
    public Word(String target) {
        this.target = target;
        this.explain = "";
    }

    public boolean isFavoriteWord() {
        return isFavoriteWord;
    }

    public void setFavoriteWord(boolean favoriteWord) {
        isFavoriteWord = favoriteWord;
    }

    /**
     * Constructs a new instance of the Word class
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Word) {
            Word newWord = (Word) o;
            if(target.equals(newWord.getTarget())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFavoriteWord);
    }
}
