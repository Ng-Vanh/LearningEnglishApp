package com.backend.OnlineDictionary.Utils;

import com.backend.OnlineDictionary.Dictionaries.GoogleTranslate;
import com.backend.OnlineDictionary.FreeDictionary;

import java.io.IOException;

public class Translation {
    private String translation;

    public Translation(String text) {
        try {
            this.translation = getGoogleTranslateTranslation(text);
        } catch (IOException e) {
            e.printStackTrace();
            this.translation = null;
        }
    }

    public String getTranslation() {
        return translation;
    }
    public String getGoogleTranslateTranslation(String text) throws IOException {
        GoogleTranslate googleTranslate = new GoogleTranslate(text);
        return googleTranslate.getTranslatedText();
    }

    public static void main(String[] args) {
        Translation translation = new Translation("hi");
        System.out.println(translation.getTranslation());
    }
}
