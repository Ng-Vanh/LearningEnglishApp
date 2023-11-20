package com.example.dictionaryenvi.Learning;

import com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWord;
import com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWord;
import com.backend.TopicWord.Utils.Definition;
import com.backend.User.UserLearnWord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.dictionaryenvi.TopicWord.TopicWord.currentUserLearnWord;

public class DailyRandomWordGenerator {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String STORE_DATE_FILE_PATH = "src/main/java/com/example/dictionaryenvi/Learning/StoreDate.txt";
    private static final String STORE_WORD_FILE_PATH = "src/main/java/com/example/dictionaryenvi/Learning/StoreWord.txt";

    private ArrayList<DetailedTopicWord> randomWordList = new ArrayList<>();

    private static String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
    private static String getStoredDate() {
        String date = "";
        try {
            File file = new File(STORE_DATE_FILE_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                date = line;
                System.out.println(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
    private static void storeDate(String date) {
        try {
            FileWriter fileWriter = new FileWriter(STORE_DATE_FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(date);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<SimpleTopicWord> generateDailyRandomWords(ArrayList<SimpleTopicWord> allWords) {
        String today = getFormattedDate();
        String previousDay = getStoredDate();
        if (!today.equals(previousDay)) {
            ArrayList<SimpleTopicWord> randomWords = generateRandomWords(allWords, 20);
            storeDate(today);
            storeWord(randomWords);
            return randomWords;
        }
        return getStoredWord();
    }
    public static void storeWord(ArrayList<SimpleTopicWord> allWords) {
        try {
            FileWriter fileWriter = new FileWriter(STORE_DATE_FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (SimpleTopicWord simpleTopicWord : allWords) {
                bufferedWriter.write(simpleTopicWord.getWord()
                + "|" + simpleTopicWord.getTopic());

            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<SimpleTopicWord> getStoredWord() {
        ArrayList<SimpleTopicWord> allDayWordList = new ArrayList<SimpleTopicWord>();
        try {
            File file = new File(STORE_DATE_FILE_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] str = line.split("|");
                String wordStr = str[0];
                String topicStr = str[1];
                SimpleTopicWord simpleTopicWord = new SimpleTopicWord(wordStr , topicStr);
                allDayWordList.add(simpleTopicWord);
                System.out.println(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allDayWordList;
    }
    public static ArrayList<SimpleTopicWord> generateRandomWords(ArrayList<SimpleTopicWord> allWords, int number) {
        ArrayList<SimpleTopicWord> randomWordList = new ArrayList<SimpleTopicWord>();
        ArrayList<Integer> randomIndexList = new ArrayList<Integer>();
        Random random = new Random();
        int cnt = 0;
        while(cnt < number) {
            int randomNumber = random.nextInt(allWords.size());
            if(!randomIndexList.contains(randomNumber)) {
                cnt++;
                randomIndexList.add(randomNumber);
            }
        }
        for (Integer randomNumber : randomIndexList) {
            randomWordList.add(allWords.get(randomNumber));
        }
        return randomWordList;
    }
}
