package com.backend;

import com.backend.ChatGPT.ChatGPT;
import java.util.Scanner;
import com.backend.ChatGPT.ChatGPT;
import com.backend.Excercise.MultipleChoice;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String query = scanner.nextLine();

//        ChatGPT bot = new ChatGPT();
//        String query = "Generate a challenging English vocabulary practice exercise in JSON format with a sentence containing a blank in the question, four options (A, B, C, D) for completion, and a specified correct answer. JSON format: {\"question\": \"\", \"options\": {\"A\": \"\", \"B\": \"\", \"C\": \"\", \"D\": \"\"}, \"correctAnswer\": \"\"}.";
//        String query = "Generate a challenging English vocabulary practice exercise in JSON-like format with a sentence containing a blank in the question, four options (A, B, C, D) for completion, and a specified correct answer. The format should follow this pattern: {question: , options: {A: , B: , C: , D: }, correctAnswer: }.";
//        String response = ChatGPT.getGPTAnswer(query);

//        System.out.println(response);

        MultipleChoice mc = new MultipleChoice();
        mc.print();

    }

}
