package com.backend.OnlineDictionary.Utils;

import com.backend.OnlineDictionary.OnlineDictionaries.GoogleTranslate;
import com.backend.OnlineDictionary.OnlineDictionaries.FreeDictionary;

import java.io.IOException;

public class Audio {
    private String audioLink;

    public Audio(String text) {
        this.audioLink = getGoogleTranslateAudioLink(text);
    }

    public String getAudioLink() {
        return audioLink;
    }

    public String getFreeDictionaryAudioLink(String text) throws IOException {
        FreeDictionary freeDictionary = new FreeDictionary();
        return freeDictionary.getAudioLink(text);
    }

    public String getGoogleTranslateAudioLink(String text) {
        GoogleTranslate googleTranslate = new GoogleTranslate();
        return googleTranslate.getAudioLink(text);
    }

    @Override
    public String toString() {
        return "Audio link: " + audioLink;
    }

    public static void main(String[] args) {
        Audio audio = new Audio("hi");
        System.out.println(audio.getAudioLink());
    }
}

