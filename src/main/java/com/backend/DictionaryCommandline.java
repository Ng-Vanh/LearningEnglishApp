package com.backend;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static com.backend.Checker.isValidWord;

public class DictionaryCommandline {
    public static void main(String args[]) {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        System.out.println("Welcome to My Application!");
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
                String wordArray[] = addedToDictionary.split(" | ");
                String target = wordArray[0];
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
                if(!isValidWord(removedFromDictionary)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.removeFromCommandLine(removedFromDictionary);
            }
            else if(userAction == 3) {
                Scanner scanner3 = new Scanner(System.in);
                String updatedToDictionary = scanner3.nextLine();
                String wordArray[] = updatedToDictionary.split(" | ");
                String target = wordArray[0];
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
                if(!isValidWord(target)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.dictionaryLookup(target);
            }
            else if(userAction == 6) {
                Scanner scanner6 = new Scanner(System.in);
                String target = scanner6.nextLine();
                if(!isValidWord(target)) {
                    System.out.println("not valid");
                    continue;
                }
                dictionaryManagement.searcher(target);
            }
            else if(userAction == 8) {
                boolean isValid = true;
                try {
                    String filePath = "src\\main\\java\\com\\backend\\dictionaries.txt";
                    File myObj = new File(filePath);
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
            else System.out.println("Action not supported");
        }
    }
}
