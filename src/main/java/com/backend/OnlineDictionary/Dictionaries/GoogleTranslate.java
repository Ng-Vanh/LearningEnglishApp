package com.backend.OnlineDictionary.Dictionaries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleTranslate {
    private String audioLink;
    private String translatedText;

    public GoogleTranslate() {

    }

    public String getAudioLink() {
        return this.audioLink;
    }

    public String getTranslatedText() {
        return this.translatedText;
    }

    public GoogleTranslate(String text) {
        String audioLink = getAudioLink(text, "en");
        String translatedText = translate(text, "en", "vi");

        this.audioLink = audioLink;
        this.translatedText = translatedText;
    }

    private static final String scriptURL = "https://script.google.com/macros/s/AKfycbyVpfwhmbUSeWveS4uNynNJjvwZTAh356HljddZArlE8MKRlqdMUSWR-0EpP65Kno1rbQ/exec";
    private static final String translateURL = "https://translate.google.com/translate_tts?ie=UTF-8&q=";
    public static String getAudioLink(String text, String languageCode) {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String url = translateURL
                + encodedText
                + "&tl="
                + languageCode
                + "&client=tw-ob";

        try {
            URL obj = URI.create(url).toURL();
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                return url; // URL is working
            } else {
                return "URL is not working"; // URL is not working
            }
        } catch (IOException e) {
            return "URL is not working"; // Exception occurred, URL is not working
        }
    }


    public static String translate(String text, String langFrom, String langTo) {
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

    public static String translateEnVi(String text) {
        return translate(text, "en", "vi");
    }

    public static String translateViEn(String text) {
        return translate(text, "vi", "en");
    }

    public static void main(String[] args) {
        GoogleTranslate translator = new GoogleTranslate("hi");
        System.out.println(translator.getTranslatedText());
        System.out.println(translator.getAudioLink());

    }
}
