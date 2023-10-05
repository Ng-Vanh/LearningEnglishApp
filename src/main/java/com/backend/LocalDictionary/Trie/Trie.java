package com.backend.LocalDictionary.Trie;

import com.backend.LocalDictionary.Dictionary.Word;

import java.util.ArrayList;

public class Trie {
    private TrieNode root;
    private char[] characterArray = new char[30];
    private int characterCount;
    ArrayList<Word> allWords = new ArrayList<Word>();

    public Trie() {
        for (char c='a' ; c<='z' ; c++) {
            characterArray[c-'a'] = c;
        }
        characterArray[26] = '-';
        characterArray[27] = ' ';
        characterCount = 28;
        root = new TrieNode();
    }

    public void insert(Word word) {
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

    public void remove(String target) {
        TrieNode currentNode = root;
        for (int i = 0; i < target.length(); i++) {
            char ch = target.charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            }
        }
        if (currentNode.getExplain() != "") {
            currentNode.setExplain("");
        }
    }

    public void update(String target, String explain) {
        TrieNode currentNode = root;
        for (int i = 0; i < target.length(); i++) {
            char ch = target.charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            }
        }
        if (currentNode.getExplain() != "") {
            currentNode.setExplain(explain);
        }
    }

    public String lookup(String target) {
        TrieNode currentNode = root;
        for (int i = 0; i < target.length(); i++) {
            char ch = target.charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            } else return "not found";
        }
        if (currentNode.getExplain() != "") {
            return currentNode.getExplain();
        }
        return "not found";
    }


    void dfsOnTrie(TrieNode currentNode, String currentTarget) {
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

    public void search(String target) {
        allWords.clear();
        TrieNode currentNode = root;
        for (int i = 0; i < target.length(); i++) {
            char ch = target.charAt(i);
            if (currentNode.hasChild(ch)) {
                currentNode = currentNode.getChildren(ch);
            } else {
                System.out.println("not found");
                return;
            }
        }
        dfsOnTrie(currentNode, target);
        for (Word word : allWords) {
            System.out.println(word.getTarget());
        }
    }
    public ArrayList<Word> getAllTrieWords() {
        allWords.clear();
        TrieNode currentNode = root;
        String currentTarget = "";
        dfsOnTrie(currentNode, currentTarget);
        return allWords;
    }
    public void showAllWord() {
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
