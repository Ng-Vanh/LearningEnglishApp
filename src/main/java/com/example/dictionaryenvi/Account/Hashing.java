package com.example.dictionaryenvi.Account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public static String hashWithSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexStringBuilder = new StringBuilder();
            for (int i = hashBytes.length - 13; i < hashBytes.length; i++) {
                String hex = String.format("%02x", hashBytes[i]);
                hexStringBuilder.append(hex);
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String password = "aaa";
        String hashedPassword = hashWithSHA256(password);

        System.out.println("Original Password: " + password);
        System.out.println("SHA-256 Hashed Password (26 characters): " + hashedPassword);
        System.out.println("Length of Hashed Password: " + hashedPassword.length());
    }
}
