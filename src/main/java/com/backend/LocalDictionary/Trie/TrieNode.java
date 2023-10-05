package com.backend.LocalDictionary.Trie;

import com.backend.LocalDictionary.Dictionary.Word;

public class TrieNode {
    private TrieNode[] children;
    private Word word;
    public int getIndexCharacter(char c) {
        if(c == '-') return 26;
        if(c == ' ') return 27;
        if(c >= 'a' && c <= 'z') {
            return c - 'a';
        }
        System.out.println("vl " + c);
        return 0;
//        return c - 'a';
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public TrieNode getChildren(char c) {
        int index = getIndexCharacter(c);
        return children[index];
    }

    public void setChildren(TrieNode[] children) {
        this.children = children;
    }

    public String getExplain() {
        return word.getExplain();
    }

    public void setExplain(String explain) {
        this.word.setExplain(explain);
    }

    /**
     * check trieNode has children
     *
     * @param c digit to be checked in the trie
     * @return true if trieNode has children c , false otherwise
     */
    public boolean hasChild(char c) {
        int index = getIndexCharacter(c);
        if (this.children[index] == null) return false;
        return true;
    }

    /**
     * add digit c to trie
     *
     * @param c digit to be added in the trie
     */
    public void addChild(char c) {
        int index = getIndexCharacter(c);
        this.children[index] = new TrieNode();
    }

    public TrieNode() {
        children = new TrieNode[30];
        word = new Word();
    }
}
