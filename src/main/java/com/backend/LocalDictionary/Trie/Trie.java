package com.backend.LocalDictionary.Trie;

import com.backend.LocalDictionary.Dictionary.Dictionary;
import com.backend.LocalDictionary.Dictionary.DictionaryManagement;
import com.backend.LocalDictionary.Dictionary.Word;

import java.util.ArrayList;

public class Trie extends Dictionary {
    private TrieNode root;
    private char[] characterArray = new char[30];
    private int characterCount;
    private ArrayList<Word> allWords = new ArrayList<Word>();

    /**
     * constructor : add character array from 'a' to 'z' and '-' ,' '.
     */
    public Trie() {
        for (char c='a' ; c<='z' ; c++) {
            characterArray[c-'a'] = c;
        }
        characterArray[26] = '-';
        characterArray[27] = ' ';
        characterCount = 28;
        root = new TrieNode();
    }
    @Override
    public void insertWord(Word word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.getTarget().length(); i++) {
            char ch = word.getTarget().charAt(i);
            if (!currentNode.hasChild(ch)) {
                currentNode.addChild(ch);
            }
            currentNode = currentNode.getChildren(ch);
        }
        currentNode.setExplain(word.getExplain());
    }
    @Override
    public void removeWord(Word word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.getTarget().length(); i++) {
            char ch = word.getTarget().charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            }
        }
        if (currentNode.getExplain() != "") {
            currentNode.setExplain("");
        }
    }
    @Override
    public void updateWord(Word word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.getTarget().length(); i++) {
            char ch = word.getTarget().charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            }
        }
        if (currentNode.getExplain() != "") {
            currentNode.setExplain(word.getExplain());
        }
    }
    @Override
    public Word lookupWord(Word word)
    {
        TrieNode currentNode = root;
        Word result = new Word();
        for (int i = 0; i < word.getTarget().length(); i++) {
            char ch = word.getTarget().charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            } else {
                result.setTarget(word.getTarget());
                result.setExplain("not found!");
                return result;
            }
        }
        if (currentNode.getExplain() != "") {
            result.setTarget(word.getTarget());
            result.setExplain(currentNode.getExplain());
            return result;
        }
        result.setTarget(word.getTarget());
        result.setExplain("not found!");
        return result;
    }


    public void dfsOnTrie(TrieNode currentNode, String currentTarget) {
        if (currentNode.getExplain() != "") {
//            System.out.println(currentNode.getExplain());
            String currentExplain = currentNode.getExplain();
            Word newWord = new Word(currentTarget, currentExplain);
            allWords.add(newWord);
        }
        for (int i=0 ; i<characterCount ; i++) {
            char c = characterArray[i];
            if (currentNode.hasChild(c)) {
                TrieNode nextNode = currentNode.getChildren(c);
                String nextTarget = currentTarget + c;
                dfsOnTrie(currentNode.getChildren(c), nextTarget);
            }
        }
    }
    @Override
    public ArrayList<Word> searchWord(Word word) {
        allWords.clear();
        TrieNode currentNode = root;
        for (int i = 0; i < word.getTarget().length(); i++) {
            char ch = word.getTarget().charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            } else {
                Word result = new Word("Not found!");
                allWords.add(result);
                return allWords;
            }
        }
        dfsOnTrie(currentNode, word.getTarget());
        return allWords;
    }
    public ArrayList<Word> getAllWords() {
        allWords.clear();
        TrieNode currentNode = root;
        String currentTarget = "";
        dfsOnTrie(currentNode, currentTarget);
        return allWords;
    }
    @Override
    public void showAllWords() {
        allWords.clear();
        TrieNode currentNode = root;
        String currentTarget = "";
        dfsOnTrie(currentNode, currentTarget);
        int num = 0;
        for (Word word : allWords) {
            num++;
            System.out.println(num + "  |  " + word.getTarget() + "  |  " + word.getExplain());
        }
    }
}
