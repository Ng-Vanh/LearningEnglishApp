package com.backend.OnlineDictionary.Utils;

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

    @Override
    public String toString() {
        return "Translation: " + translation + '\n'
                + "Audio link: " + audioLink;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; ++i) {
            AudioTranslation audioTranslation = new AudioTranslation("Hello how are you");
            System.out.println(audioTranslation.getAudioLink());
            System.out.println(audioTranslation.getTranslation());
        }


//        System.out.println(audioTranslation);
    }
}
