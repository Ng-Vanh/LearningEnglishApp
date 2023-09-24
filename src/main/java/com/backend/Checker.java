package com.backend;
public class Checker {
    public static boolean isValidWord(String word) {
        for (int i = 0; i < word.length(); ++i) {
            if (!Character.isLetterOrDigit(word.charAt(i))) {
                System.out.println(word.charAt(i));
                return false;
            }
        }
        return true;
    }
}
