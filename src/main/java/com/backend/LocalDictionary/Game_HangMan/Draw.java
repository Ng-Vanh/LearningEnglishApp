package com.backend.LocalDictionary.Game_HangMan;

import com.backend.LocalDictionary.Dictionary.Word;

public class Draw extends Hangman {
    public Draw(Word answerWord, Word secretWord,
                Word correctChars,
                int incorrectGuess, Word incorrectChars) {
        super(answerWord , secretWord , correctChars , incorrectGuess , incorrectChars);
    }
    public String getDrawing(int i)
    {
        int LEN = 8;
        String[] FIGURES = {
                        "   -------------    \n" +
                        "   |                \n" +
                        "   |                \n" +
                        "   |                \n" +
                        "   |                \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |                \n" +
                        "   |                \n" +
                        "   |                \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |           O    \n" +
                        "   |                \n" +
                        "   |                \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |           O    \n" +
                        "   |           |    \n" +
                        "   |                \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |           O    \n" +
                        "   |          /|\\  \n" +
                        "   |                \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |           O    \n" +
                        "   |          /|\\  \n" +
                        "   |          /     \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |           O    \n" +
                        "   |          /|\\  \n" +
                        "   |          / \\  \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   -------------    \n" +
                        "   |           |    \n" +
                        "   |           O    \n" +
                        "   |          /|\\  \n" +
                        "   |          / \\  \n" +
                        "   |                \n" +
                        " -----              \n",
        };
        return FIGURES[i % LEN];
    }
    public String getNextHangman(int index) {
        int LEN = 4;
        String[] FIGURES = {
                        "   ------------+    \n" +
                        "   |          /     \n" +
                        "   |         O      \n" +
                        "   |        /|\\    \n" +
                        "   |        / \\    \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   ------------+     \n" +
                        "   |           |     \n" +
                        "   |           O     \n" +
                        "   |          /|\\   \n" +
                        "   |          / \\   \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   ------------+      \n" +
                        "   |            \\    \n" +
                        "   |            O     \n" +
                        "   |           /|\\   \n" +
                        "   |           / \\   \n" +
                        "   |                \n" +
                        " -----              \n",
                        "   ------------+     \n" +
                        "   |           |     \n" +
                        "   |           O     \n" +
                        "   |          /|\\   \n" +
                        "   |          / \\   \n" +
                        "   |                \n" +
                        " -----              \n",
        };
        return FIGURES[index];
    }
    public String getNextStandingMan(int index) {
        String[] FIGURES = {
                        "     O     \n" +
                        "    /|\\   \n" +
                        "    | |    \n",
                        "     O     \n" +
                        "    /|\\   \n" +
                        "    / \\   \n",
                        "   __O__   \n" +
                        "     |     \n" +
                        "    / \\   \n",
                        "    \\O/   \n" +
                        "     |     \n" +
                        "    / \\   \n",
                        "   __O__   \n" +
                        "     |     \n" +
                        "    / \\   \n",
                        "     O     \n" +
                        "    /|\\   \n" +
                        "    / \\   \n",
                        "     O     \n" +
                        "    /|\\   \n" +
                        "    / \\   \n",
                        "      O     \n" +
                        "    /|\\   \n" +
                        "    / \\   \n",
                        "     O     \n" +
                        "    /|\\   \n" +
                        "    / \\   \n",
        };
        return FIGURES[index];
    }
    public void update(Word answerWord, Word secretWord,
                       Word correctChars,
                       int incorrectGuess, Word incorrectChars) {
        this.answerWord = answerWord;
        this.secretWord = secretWord;
        this.correctChars = correctChars;
        this.incorrectGuess = incorrectGuess;
        this.incorrectChars = incorrectChars;
    }
    public void printStarts() {
        System.out.println("Current Word: " + secretWord.getTarget());
        System.out.print("Correct guesses: " + correctChars.getTarget());
        System.out.println("\tIncorrect guesses: " + incorrectChars.getTarget());
        if(secretWord.equals(answerWord)) {
            System.out.println("Well done :D   The word is: " + answerWord.getTarget());
        }
        else if(incorrectGuess == MAX_MISTAKES - 1) {
            System.out.println("You lose :( The word is:" + answerWord.getTarget());
        }
        else {
            System.out.println("\tChoose a character: ");
        }
    }
    public void printScreen()
    {
        for (int i = 0; i < 30; ++i) System.out.println();
        System.out.println(getDrawing(incorrectGuess));
        printStarts();
    }
    public void playAnimation() {
        int LEN_STANDING_MAN = 8;
        int NEXT_HANG_MAN = 4;
        for (int i=0 ; i<21 ; i++) {
            for (int j=0 ; j<30 ; j++) System.out.println();
            if(secretWord.equals(answerWord)) {
                System.out.println(getNextStandingMan(i % LEN_STANDING_MAN));
            }
            else if(incorrectGuess == MAX_MISTAKES - 1) {
                System.out.println(getNextHangman(i % NEXT_HANG_MAN));
            }
            printStarts();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
    }
}
