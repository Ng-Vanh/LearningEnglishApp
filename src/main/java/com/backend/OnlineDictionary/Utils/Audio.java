package com.backend.OnlineDictionary.Utils;

import com.backend.OnlineDictionary.Dictionaries.GoogleTranslate;
import com.backend.OnlineDictionary.Dictionaries.FreeDictionary;

import java.io.IOException;

public class Audio {
    private String audioLink;

    public Audio(String text) {
//        try {
//            this.audioLink = getFreeDictionaryAudioLink(text);
//        } catch (IOException e) {
//            try {
//                this.audioLink = getGoogleTranslateAudioLink(text);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                this.audioLink = null;
//            }
//        }

        try {
            this.audioLink = getGoogleTranslateAudioLink(text);
        } catch (IOException e) {
            e.printStackTrace();
            this.audioLink = null;
        }
    }

    public String getAudioLink() {
        return audioLink;
    }

    public String getFreeDictionaryAudioLink(String text) throws IOException {
        FreeDictionary freeDictionary = new FreeDictionary();
        return freeDictionary.getAudioLink(text);
    }

    public String getGoogleTranslateAudioLink(String text) throws IOException {
        return GoogleTranslate.getAudioLink(text, "en");
    }

    public static void main(String[] args) {
        Audio audio = new Audio("hi");
        System.out.println(audio.getAudioLink());
    }
}

