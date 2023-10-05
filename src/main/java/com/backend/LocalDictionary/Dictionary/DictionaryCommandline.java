package com.backend.LocalDictionary.Dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;
import static com.backend.LocalDictionary.Dictionary.Checker.isValidWord;

public class DictionaryCommandline {
    private static final String FILE_PATH = "src\\main\\java\\com\\backend\\dictionaries.txt";
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
                target = target.toLowerCase();
                String explain = wordArray[1];
                if(!isValidWord(target) || !isValidWord(explain)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.insertFromCommandLine(target , explain);
            }
            else if(userAction == 2) {
                Scanner scanner2 = new Scanner(System.in);
                String removedFromDictionary = scanner2.nextLine();
                removedFromDictionary = removedFromDictionary.toLowerCase();
                if(!isValidWord(removedFromDictionary)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.removeFromCommandLine(removedFromDictionary);
            }
            else if(userAction == 3) {
                Scanner scanner3 = new Scanner(System.in);
                String updatedToDictionary = scanner3.nextLine();
                String wordArray[] = updatedToDictionary.split("<html>");
                String target = wordArray[0];
                target = target.toLowerCase();
                String explain = wordArray[1];
                if(!isValidWord(target) || !isValidWord(explain)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.updateFromCommandLine(target , explain);
            }
            else if(userAction == 4) {
                dictionaryManagement.showAllWord();
            }
            else if(userAction == 5) {
                Scanner scanner5 = new Scanner(System.in);
                String target = scanner5.nextLine();
                target = target.toLowerCase();
                if(!isValidWord(target)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.dictionaryLookup(target);
            }
            else if(userAction == 6) {
                Scanner scanner6 = new Scanner(System.in);
                String target = scanner6.nextLine();
                target = target.toLowerCase();
                if(!isValidWord(target)) {
                    System.out.println("not valid.");
                    continue;
                }
                dictionaryManagement.searcher(target);
            }
            else if(userAction == 8) {
                boolean isValid = true;
                try {
                    File myObj = new File(FILE_PATH);
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String wordArray[] = data.split(" ");
                        String target = wordArray[0];
                        String explain = wordArray[1];
                        dictionaryManagement.insertFromCommandLine(target , explain);
                    }
                    if(!isValid)    continue;
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            else if(userAction == 9) {
                try {
                    File file = new File(FILE_PATH);
                    if(file.exists()) {
                        file.delete();
                    }
                    FileWriter fileWriter = new FileWriter(FILE_PATH);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    ArrayList<Word> allWords = dictionaryManagement.getAllWords();
                    for (Word word : allWords) {
//                        System.out.println(word.getTarget());
                        printWriter.println(word.getTarget() + "<html>" + word.getExplain());
                    }
                    printWriter.close();
                    System.out.println("Data has been written to the file: " + FILE_PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else System.out.println("Action not supported");
        }
    }
}
