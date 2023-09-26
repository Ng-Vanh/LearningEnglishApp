package com.backend.OnlineDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslate {
    //private static final String webAppURL = "https://script.google.com/macros/s/AKfycby-pdZHY15gtLAGVPyJGF26kWRHD0j2R6NrLqSN2hg7XCMDoLRkmycHOrmWBJyg8A6Zyw/exec";
    private static final String webAppURL = "https://script.google.com/macros/s/AKfycbyVpfwhmbUSeWveS4uNynNJjvwZTAh356HljddZArlE8MKRlqdMUSWR-0EpP65Kno1rbQ/exec";

    public static String translate(String text, String langFrom, String langTo) {
        try {
            String urlStr = webAppURL +
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
            e.printStackTrace(); // You should replace this with proper error handling
            return "Error: Unable to perform translation";
        }
    }

    public static String translateEnVi(String text) {
        return translate(text, "en", "vi");
    }

    public static String translateViEn(String text) {
        return translate(text, "vi", "en");
    }
}
