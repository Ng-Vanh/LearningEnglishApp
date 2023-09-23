package com.backend.chatGPT;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();

        chatGPT bot = new chatGPT();
        String response = bot.getAnswer(query);
        System.out.println(response);
    }

}
