package com.backend.OnlineDictionary.OnlineDictionaries;

import com.backend.OnlineDictionary.Utils.AudioTranslation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleTranslate extends AudioTranslation  {

    @Override
    public String getAudioLink() {
        return this.audioLink;
    }

    @Override
    public String getTranslation() {
        return this.translation;
    }

    public GoogleTranslate() {

    }

    public GoogleTranslate(String text) {
        String audioLink = getAudioLink(text, "en");
        String translation = getTranslation(text, "en", "vi");

        this.audioLink = audioLink;
        this.translation = translation;
    }

    public GoogleTranslate(String text, String langFrom, String langTo) {
        String audioLink = getAudioLink(text, langFrom);
        String translation = getTranslation(text, langFrom, langTo);

        this.audioLink = audioLink;
        this.translation = translation;
    }

    private static final String scriptURL = "https://script.google.com/macros/s/AKfycbyVpfwhmbUSeWveS4uNynNJjvwZTAh356HljddZArlE8MKRlqdMUSWR-0EpP65Kno1rbQ/exec";
    private static final String translateURL = "https://translate.google.com/translate_tts?ie=UTF-8&q=";

    public String getAudioLink(String text, String languageCode) {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String url = translateURL
                + encodedText
                + "&tl="
                + languageCode
                + "&client=tw-ob";

        return url;
    }

    public String getTranslation(String text, String langFrom, String langTo) {
        try {
            String urlStr = scriptURL +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;

            URL obj = URI.create(urlStr).toURL();
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Unable to perform translation";
        }
    }

    @Override
    public String getAudioLink(String text) {
        return getAudioLink(text, "en");
    }

    @Override
    public String getTranslation(String text) {
        return getTranslation(text, "en", "vi");
    }

    @Override
    public String toString() {
        return "Translation text : " + translation + "\n"
                + "Audio link: " + audioLink;
    }

    public static void main(String[] args) {
        // Initialization with text will create audio link and translation.
        GoogleTranslate googleTranslate = new GoogleTranslate("Hello how are you");
        System.out.println(googleTranslate.getTranslation());
        System.out.println(googleTranslate.getAudioLink());

        // Initialization without text will not create audio link and translation.
        GoogleTranslate googleTranslate_Empty = new GoogleTranslate();
        System.out.println(googleTranslate_Empty.getAudioLink("what time is it?"));
        System.out.println(googleTranslate_Empty.getTranslation("what's the weather like?"));
    }
}
