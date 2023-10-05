package com.backend.LocalDictionary.Trie;

import com.backend.LocalDictionary.Dictionary.Word;

public class TrieNode {
    private TrieNode[] children;
    private Word word;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public TrieNode getChildren(char c) {
        return children[c - 'a'];
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
     * check trieNode has children.
     *
     * @param c digit to be checked in the trie
     * @return true if trieNode has children c , false otherwise
     */
    public boolean hasChild(char c) {
        if (this.children[c - 'a'] == null) return false;
        return true;
    }

    /**
     * add digit c to trie
     *
     * @param c digit to be added in the trie
     */
    public void addChild(char c) {
        this.children[c - 'a'] = new TrieNode();
    }

    public TrieNode() {
        children = new TrieNode[26];
        word = new Word();
    }
}
