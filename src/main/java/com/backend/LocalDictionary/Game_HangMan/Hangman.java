package com.backend.LocalDictionary.Game_HangMan;

import com.backend.LocalDictionary.Dictionary.DictionaryManagement;
import com.backend.LocalDictionary.Dictionary.Word;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    protected Word answerWord;
    protected Word secretWord;

    protected Word correctChars;
    protected int incorrectGuess;
    protected Word incorrectChars;
    protected static final int MAX_MISTAKES = 8;
    public Hangman() {
        incorrectGuess = 0;
        secretWord = new Word();
        answerWord = new Word();
        correctChars = new Word();
        incorrectChars = new Word();
    }

    public Hangman(Word answerWord, Word secretWord, Word correctChars, int incorrectGuess, Word incorrectChars) {
        this.answerWord = answerWord;
        this.secretWord = secretWord;
        this.correctChars = correctChars;
        this.incorrectGuess = incorrectGuess;
        this.incorrectChars = incorrectChars;
    }

    /**
     * generate random number between min and max.
     * @param min minimum number.
     * @param max maximum number.
     * @return random number.
     */
    public int generateRandomNumber(int min , int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }

    public boolean isCharInWord(char ch) {
        for (int i=0 ; i<this.answerWord.getTarget().length() ; i++) {
            if(this.answerWord.getTarget().charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }

    public void generateHiddenCharaters() {
        String str = "";
        for (int i=0 ; i<this.answerWord.getTarget().length() ; i++) {
            str += "-";
        }
        this.secretWord.setTarget(str);
    }

    public void updateSecretWord(char ch) {
        char[] charArray = this.secretWord.getTarget().toCharArray();
        for (int i=0 ; i<this.answerWord.getTarget().length() ; i++ ) {
            if(ch == this.answerWord.getTarget().charAt(i)) {
                charArray[i] = ch;
            }
        }
        this.secretWord.setTarget(new String(charArray));
    }
    public void updateEnteredChars(char ch , boolean isCorrect) {
        if(isCorrect) {
            correctChars.setTarget(correctChars.getTarget() + ch + " ");
        }
        else {
            incorrectChars.setTarget(incorrectChars.getTarget() + ch + " ");
        }
    }
    public void updateIncorrectGuess() {
        this.incorrectGuess += 1;
    }
    public void processData(char ch) {
        if(isCharInWord(ch)) {
            updateSecretWord(ch);
            updateEnteredChars(ch , true);
        }
        else {
            updateIncorrectGuess();
            updateEnteredChars(ch , false);
        }
    }
    public void play() {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        ArrayList<Word> allWords = dictionaryManagement.getAllWords();
        int index = generateRandomNumber(0 ,allWords.size()-1);
        answerWord = allWords.get(index);
        generateHiddenCharaters();
        Draw draw = new Draw(answerWord , secretWord , correctChars , incorrectGuess , incorrectChars);
        draw.printScreen();
        do {
            Scanner scanner = new Scanner(System.in);
            char ch = scanner.next().charAt(0);
            processData(ch);
            draw.update(answerWord , secretWord , correctChars , incorrectGuess , incorrectChars);
            draw.printScreen();
        }
        while(!secretWord.equals(answerWord) && incorrectGuess != MAX_MISTAKES - 1);
        draw.update(answerWord , secretWord , correctChars , incorrectGuess , incorrectChars);
        draw.playAnimation();
    }
}
