package com.backend.LocalDictionary.Dictionary;

import com.backend.Connection.WordDataAccess;
import com.backend.LocalDictionary.Trie.Trie;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

public class DictionaryManagement extends Trie {
    private static final String DATA_INPUT_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\dictionaries.txt";
    private static final String DATA_OUTPUT_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\dictionaries.txt";
    private static final String FAVORITE_WORD_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\favorite_word.txt";
    private static final String HISTORY_SEARCH_FILE_PATH = "src\\main\\java\\com\\backend\\LocalDictionary\\history_search.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Trie trie = new Trie();
    private Set<Word> favoriteWord = new HashSet<Word>();
    private Set<Word> historySearch = new HashSet<Word>();

    public DictionaryManagement() {
        try {
            readDataFromTxtFile();
            readFavoriteWordFromTxtFile();
            readHistorySearchFromTxtFile();
//            readDataFromDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read data from txt file to trie.
     *
     * @throws IOException exception.
     */
    public void readDataFromTxtFile() throws IOException {
        FileReader fis = new FileReader(DATA_INPUT_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String target = parts[0];
            String explain = SPLITTING_CHARACTERS + parts[1];
            Word word = new Word(target, explain);
            insertWord(word);
        }
    }

    public void readFavoriteWordFromTxtFile() throws IOException {
        FileReader fis = new FileReader(FAVORITE_WORD_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
//            System.out.println("Read " + line);
            Word word = new Word(line);
            favoriteWord.add(word);
        }
    }
    public void readHistorySearchFromTxtFile() throws IOException {
        FileReader fis = new FileReader(HISTORY_SEARCH_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
//            System.out.println("Read " + line);
            Word word = new Word(line);
            historySearch.add(word);
        }
    }

    /**
     * read data from database to trie.
     *
     * @throws IOException
     */
    public void readDataFromDatabase() throws IOException {
        WordDataAccess wordDataAccess = new WordDataAccess();
        ArrayList<Word> allWords = wordDataAccess.selectAll();
        for (Word word : allWords) {
            insertWord(word);
        }
    }

    /**
     * export data from trie to txt file.
     */
    public void exportDataToTxtFile() {
        try {
            File file = new File(DATA_OUTPUT_FILE_PATH);
            if (file.exists()) {
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

    /**
     * insert word to trie.
     *
     * @param word word.
     */
    @Override
    public void insertWord(Word word) {
        word.setTarget(word.getTarget().toLowerCase());
        if (!isValidWord(word.getTarget())) {
//            System.out.println(word.getTarget() + " not valid!");
        } else {
            super.insertWord(word);
        }
    }

    /**
     * remove word from trie.
     *
     * @param word word.
     */
    @Override
    public void removeWord(Word word) {
        word.setTarget(word.getTarget().toLowerCase());
        if (!isValidWord(word.getTarget())) {
            System.out.println(word.getTarget() + " not valid!");
        } else {
            super.removeWord(word);
        }
    }

    /**
     * update word target.
     *
     * @param word word.
     */
    @Override
    public void updateWord(Word word) {
        word.setTarget(word.getTarget().toLowerCase());
        if (!isValidWord(word.getTarget())) {
            System.out.println(word.getTarget() + " not valid!");
        } else {
            super.updateWord(word);
        }
    }

    /**
     * show all word from trie.
     */
    @Override
    public void showAllWords() {
        super.showAllWords();
    }

    /**
     * look up word from trie.
     *
     * @param word word.
     * @return explain of word target.
     */
    @Override
    public Word lookupWord(Word word) {
        word.setTarget(word.getTarget().toLowerCase());
        Word result = new Word();
        if (!isValidWord(word.getTarget())) {
            result.setExplain(word.getTarget() + " not valid");
        }
        result = super.lookupWord(word);
        return result;
    }

    /**
     * search word from trie.
     *
     * @param word word.
     * @return list word result started with target.
     */
    @Override
    public ArrayList<Word> searchWord(Word word) {
        word.setTarget(word.getTarget().toLowerCase());
        ArrayList<Word> result = new ArrayList<Word>();
        if (!isValidWord(word.getTarget())) {
            Word newWord = new Word();
            newWord.setExplain(word.getTarget() + " not valid");
            result.add(newWord);
            return result;
        }
        return super.searchWord(word);
    }

    /**
     * add favorite word to set and export to file favorite_word.txt.
     *
     * @param word word.
     */
    public void addFavoriteWord(Word word) {
        if (!favoriteWord.contains(word)) {
            favoriteWord.add(word);
            try {
                File file = new File(FAVORITE_WORD_FILE_PATH);
                FileWriter fileWriter = new FileWriter(FAVORITE_WORD_FILE_PATH, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(word.getTarget());
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void removeFavoriteWord(Word word) {
        favoriteWord.remove(word);
        try {
            File file = new File(FAVORITE_WORD_FILE_PATH);
            FileWriter fileWriter = new FileWriter(FAVORITE_WORD_FILE_PATH, false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (Word word1 : favoriteWord) {
                printWriter.println(word1.getTarget());
            }
//            printWriter.println(word.getTarget());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * add history search word to set and export to file history_search.txt.
     *
     * @param word word.
     */
    public void addHistorySearch(Word word) {
        if (!historySearch.contains(word)) {
            historySearch.add(word);
            try {
                File file = new File(HISTORY_SEARCH_FILE_PATH);
                FileWriter fileWriter = new FileWriter(HISTORY_SEARCH_FILE_PATH, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(word.getTarget());
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<Word> getFavoriteWord() {
        return favoriteWord;
    }

    public Set<Word> getHistorySearch() {
        return historySearch;
    }

    /**
     * check if word is favorite or not.
     *
     * @param word word.
     * @return true if favorite , false otherwise.
     */
    public boolean checkIsFavoriteWord(Word word) {
        if (favoriteWord.contains(word)) {
            return true;
        }
        return false;
    }

}
