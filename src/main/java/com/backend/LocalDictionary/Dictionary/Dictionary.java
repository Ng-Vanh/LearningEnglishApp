package com.backend.LocalDictionary.Dictionary;

import com.backend.LocalDictionary.Trie.TrieNode;
import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Dictionary {

    TrieNode trieNode = new TrieNode();
    Word word = new Word();
    public void addTo(String target , String explain) {
        word.setTarget(target);
        word.setExplain(explain);
        trieNode.setWord(word);
    }

    public Dictionary() {
        trieNode = new TrieNode();
        word = new Word();
    }
}
