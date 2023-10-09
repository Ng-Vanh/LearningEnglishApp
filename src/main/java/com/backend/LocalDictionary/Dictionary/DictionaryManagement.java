package com.backend.LocalDictionary.Dictionary;

import com.backend.Connection.WordDataAccess;
import com.backend.LocalDictionary.Dictionary.Dictionary;
import com.backend.LocalDictionary.Trie.Trie;
import com.backend.Connection.WordDataAccess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

public class DictionaryManagement {
    private static final String DATA_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\E_V.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    Trie trie = new Trie();
    Dictionary dictionary = new Dictionary();
    public DictionaryManagement () {
        try {
//            readDataFromTxtFile();
            readDataFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readDataFromTxtFile() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String target = parts[0];
            String explain = SPLITTING_CHARACTERS + parts[1];
            target = target.toLowerCase();
            if(isValidWord(target)) {
                dictionary.addTo(target , explain);
                trie.insert(dictionary.word);
            }
        }
    }
    public void readDataFromDatabase() throws IOException {
        WordDataAccess wordDataAccess = new WordDataAccess();
        ArrayList<Word> allWords = wordDataAccess.getAllWord();
        for (Word word : allWords) {
            String target = word.getTarget();
            String explain = word.getExplain();
            target = target.toLowerCase();
            if(isValidWord(target)) {
                dictionary.addTo(target , explain);
                trie.insert(dictionary.word);
            }
        }
    }
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
    public ArrayList<Word> getAllWords() {
        return trie.getAllTrieWords();
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
