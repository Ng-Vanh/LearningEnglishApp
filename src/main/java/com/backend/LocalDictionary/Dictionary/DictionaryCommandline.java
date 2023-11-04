package com.backend.LocalDictionary.Dictionary;

import com.backend.LocalDictionary.Game_HangMan.Hangman;

import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;
import java.util.Set;

import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

public class DictionaryCommandline {
    public static void main(String args[]) throws FileNotFoundException {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        System.out.println("Welcome to My Application! ");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import from file");
        System.out.println("[9] Export from file");
        System.out.println("Your action:");
        while(true) {
            Scanner scanner = new Scanner(System.in);
            if(!scanner.hasNextInt()) {
                System.out.println("Action not supported");
                continue;
            }
            int userAction = scanner.nextInt();
            if(userAction == 0) System.exit(0);
            else if(userAction == 1) {
                Scanner scanner1 = new Scanner(System.in);
                String addedToDictionary = scanner1.nextLine();
                String wordArray[] = addedToDictionary.split("<html>");
                String target = wordArray[0];
                String explain = wordArray[1];
                Word word = new Word(target , explain);
                dictionaryManagement.insertWord(word);
            }
            else if(userAction == 2) {
                Scanner scanner2 = new Scanner(System.in);
                String removedFromDictionary = scanner2.nextLine();
                Word word = new Word(removedFromDictionary);
                dictionaryManagement.removeWord(word);
            }
            else if(userAction == 3) {
                Scanner scanner3 = new Scanner(System.in);
                String updatedToDictionary = scanner3.nextLine();
                String wordArray[] = updatedToDictionary.split("<html>");
                String target = wordArray[0];
                String explain = wordArray[1];
                Word word = new Word(target , explain);
                dictionaryManagement.updateWord(word);
            }
            else if(userAction == 4) {
                dictionaryManagement.showAllWords();
            }
            else if(userAction == 5) {
                Scanner scanner5 = new Scanner(System.in);
                String target = scanner5.nextLine();
                Word word = new Word(target);
                Word result = dictionaryManagement.lookupWord(word);
                System.out.println(result.getExplain());
            }
            else if(userAction == 6) {
                Scanner scanner6 = new Scanner(System.in);
                String target = scanner6.nextLine();
                Word word = new Word(target);
                ArrayList<Word> allWords = dictionaryManagement.searchWord(word);
                for (Word result : allWords) {
                    System.out.println(result.getTarget());
                }
            }
            else if(userAction == 7) {
                Hangman hangman = new Hangman();
                hangman.play();
            }
            else if(userAction == 8) {
                try {
                    dictionaryManagement.readDataFromTxtFile();
//                    dictionaryManagement.readDataFromDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(userAction == 9) {
                dictionaryManagement.exportDataToTxtFile();
            }
            else System.out.println("Action not supported");
        }
    }
}
