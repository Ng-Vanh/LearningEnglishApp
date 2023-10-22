package com.backend.LocalDictionary.Dictionary;

import com.backend.LocalDictionary.Trie.TrieNode;
import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Dictionary {
    protected Word word = new Word();
    public Word getWord() {
        return this.word;
    }

    /**
     *
     * @param target target.
     * @param explain explain.
     */
    public void addTo(String target , String explain) {
        word.setTarget(target);
        word.setExplain(explain);
    }

    public Dictionary() {
        word = new Word();
    }
    public abstract void insertWord(Word word);
    public abstract void removeWord(Word word);
    public abstract void updateWord(Word word);
    public abstract void showAllWords();
    public abstract ArrayList<Word> searchWord(Word word);
    public abstract Word lookupWord(Word word);
}
