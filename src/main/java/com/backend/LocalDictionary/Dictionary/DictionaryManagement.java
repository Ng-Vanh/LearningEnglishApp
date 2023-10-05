package com.backend.LocalDictionary.Dictionary;

import com.backend.LocalDictionary.Dictionary.Dictionary;
import com.backend.LocalDictionary.Trie.Trie;

public class DictionaryManagement {
    Trie trie = new Trie();
    Dictionary dictionary = new Dictionary();
    public void insertFromCommandLine(String target , String explain) {
        dictionary.addTo(target , explain);
        trie.insert(dictionary.word);
    }
    public void removeFromCommandLine(String target) {
        trie.remove(target);
    }

    public void updateFromCommandLine(String target , String explain) {
        trie.update(target , explain);
    }

    public void showAllWord() {

        trie.showAllWord();
    }
    public void dictionaryLookup(String target) {
        String result = trie.lookup(target);
        System.out.println(result);
    }
    public void searcher(String target) {
        trie.search(target);
    }
}
