package com.backend.LocalDictionary.Dictionary;

import com.backend.Connection.WordDataAccess;
import com.backend.LocalDictionary.Dictionary.Dictionary;
import com.backend.LocalDictionary.Trie.Trie;
import com.backend.Connection.WordDataAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

public class DictionaryManagement {
    private static final String DATA_INPUT_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\dictionaries.txt";
    private static final String DATA_OUTPUT_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\dictionaries.txt";
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
        FileReader fis = new FileReader(DATA_INPUT_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while((line = br.readLine()) != null) {
//            System.out.println(line);
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String target = parts[0];
            String explain = SPLITTING_CHARACTERS + parts[1];
            insertFromCommandLine(target , explain);
        }
    }
    public void readDataFromDatabase() throws IOException {
        WordDataAccess wordDataAccess = new WordDataAccess();
        ArrayList<Word> allWords = wordDataAccess.selectAll();
        for (Word word : allWords) {
            String target = word.getTarget();
            String explain = word.getExplain();
            insertFromCommandLine(target , explain);
        }
    }
    public void exportDataToTxtFile() {
        try {
            File file = new File(DATA_OUTPUT_FILE_PATH);
            if(file.exists()) {
//                System.out.println("deleted");
                file.delete();
            }
            FileWriter fileWriter = new FileWriter(DATA_OUTPUT_FILE_PATH);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ArrayList<Word> allWords = getAllWords();
            for (Word word : allWords) {
//                        System.out.println(word.getTarget());
                printWriter.println(word.getTarget() + word.getExplain());
            }
            printWriter.close();
            System.out.println("Data has been written to the file: " + DATA_OUTPUT_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insertFromCommandLine(String target , String explain) {
        target = target.toLowerCase();
        if(isValidWord(target)) {
            dictionary.addTo(target , explain);
            trie.insert(dictionary.word);
        }
//        else {
//            System.out.println(target + "not valid!");
//        }
    }
    public void removeFromCommandLine(String target) {
        target = target.toLowerCase();
        if(isValidWord(target)) {
            trie.remove(target);
        }
        else {
            System.out.println("not valid!");
        }
    }
    public void updateFromCommandLine(String target , String explain) {
        target = target.toLowerCase();
        if(isValidWord(target)) {
            trie.update(target , explain);
        }
        else {
            System.out.println("not valid!");
        }
    }
    public ArrayList<Word> getAllWords() {
        return trie.getAllTrieWords();
    }
    public void showAllWord() {
        trie.showAllWord();
    }
    public String dictionaryLookup(String target) {
        target = target.toLowerCase();
        String result = "not valid!";
        if(isValidWord(target)) {
            result = trie.lookup(target);
        }
        return result;
    }
    public ArrayList searcher(String target) {
        target = target.toLowerCase();
        ArrayList<Word> result = new ArrayList<>();
        if(isValidWord(target)) {
            result = trie.search(target);
        }
        return result;
    }
}
