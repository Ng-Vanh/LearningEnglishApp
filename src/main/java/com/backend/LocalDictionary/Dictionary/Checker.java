package com.backend.LocalDictionary.Dictionary;
public class Checker {
    public static boolean isValidWord(String word) {
<<<<<<< HEAD
        for (int i = 0; i < word.length(); ++i) {
            if(word.charAt(i) == '-' || word.charAt(i) == ' ')  continue;
            char c = word.charAt(i);
            if(c < 'a' || c > 'z')  return false;
//            if (!Character.isLetterOrDigit(word.charAt(i))) {
////                System.out.println(word.charAt(i));
//                return false;
//            }
=======
        for (int i = 0; i < word.length() ; ++i) {
            if (!Character.isLetterOrDigit(word.charAt(i))) {
                System.out.println(word.charAt(i));
                return false;
            }
>>>>>>> DucDev
        }
        return true;
    }
}
