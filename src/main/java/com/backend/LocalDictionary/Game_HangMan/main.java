package com.backend.LocalDictionary.Game_HangMan;

import com.backend.LocalDictionary.Dictionary.DictionaryManagement;
import com.backend.LocalDictionary.Dictionary.Word;

import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String args[]) {
        Hangman hangman = new Hangman();
        hangman.play();
    }
}
