package com.backend.OnlineDictionary.Utils;

import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;

public abstract class AudioTranslation {
    protected String audioLink;
    protected String translation;

    public AudioTranslation() {

    }

    public abstract String getAudioLink();

    public abstract String getAudioLink(String text);

    public abstract String getTranslation();

    public abstract String getTranslation(String text);

    @Override
    public abstract String toString();

    public static void main(String[] args) {
        // Initialization with text will create audio link and translation.
        AudioTranslation googleTranslate = new GoogleTranslate("Hello how are you");
        System.out.println(googleTranslate.getAudioLink());
        System.out.println(googleTranslate.getTranslation());
        System.out.println(googleTranslate);

        // Initialization without text will NOT create audio link and translation.
        AudioTranslation googleTranslate_Empty = new GoogleTranslate();
        System.out.println(googleTranslate_Empty.getTranslation("what time is it?"));
        System.out.println(googleTranslate_Empty.getAudioLink("what's the weather like?"));
        System.out.println(googleTranslate_Empty);
    }
}
