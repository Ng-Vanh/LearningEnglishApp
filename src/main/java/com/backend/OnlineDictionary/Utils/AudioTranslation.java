package com.backend.OnlineDictionary.Utils;

import java.io.IOException;

public class AudioTranslation {
    private String audioLink;
    private String translation;

    public AudioTranslation(String text) {
        Translation translation = new Translation(text);
        this.translation = translation.getTranslation();

        Audio audio = new Audio(text);
        this.audioLink = audio.getAudioLink();
    }

    public String getAudioLink() {
        return audioLink;
    }

    public String getTranslation() {
        return translation;
    }

    public static void main(String[] args) {
        AudioTranslation audioTranslation = new AudioTranslation("hello how are you?");
        System.out.println(audioTranslation.getAudioLink());
        System.out.println(audioTranslation.getTranslation());
    }
}
