package com.backend.Exercise.Utils;

import com.backend.Exercise.Exercises.Dictation.Dictation;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoice;
import com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWord;
import com.backend.TopicWord.TopicWords.SimpleTopicWord.SimpleTopicWord;

import java.util.ArrayList;

import static com.backend.TopicWord.TopicWords.DetailedTopicWord.DetailedTopicWordLoader.getDetailedTopicWordListFromSimpleTopicWordList;

public class ExerciseLoader {
    public static ArrayList<Exercise> getExerciseListFromSimpleTopicWordList(ArrayList<SimpleTopicWord> simpleTopicWordList) {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        ArrayList<DetailedTopicWord> detailedTopicWordList = getDetailedTopicWordListFromSimpleTopicWordList(simpleTopicWordList);
        for (DetailedTopicWord detailedTopicWord: detailedTopicWordList) {
            Exercise exercise = detailedTopicWord.getQuiz().getExercise();
            exerciseList.add(exercise);
        }
        return exerciseList;
    }

    public static ArrayList<MultipleChoice> getMultipleChoiceListFromSimpleTopicWordList(ArrayList<SimpleTopicWord> simpleTopicWordList) {
        ArrayList<MultipleChoice> multipleChoiceList = new ArrayList<>();
        ArrayList<DetailedTopicWord> detailedTopicWordList = getDetailedTopicWordListFromSimpleTopicWordList(simpleTopicWordList);
        for (DetailedTopicWord detailedTopicWord: detailedTopicWordList) {
            Exercise exercise = detailedTopicWord.getQuiz().getExercise();
            if (exercise instanceof MultipleChoice) {
                multipleChoiceList.add((MultipleChoice) exercise);
            }
        }
        return multipleChoiceList;
    }

    public static ArrayList<Dictation> getDictationListFromSimpleTopicWordList(ArrayList<SimpleTopicWord> simpleTopicWordList) {
        ArrayList<Dictation> dictationList = new ArrayList<>();
        ArrayList<DetailedTopicWord> detailedTopicWordList = getDetailedTopicWordListFromSimpleTopicWordList(simpleTopicWordList);
        for (DetailedTopicWord detailedTopicWord: detailedTopicWordList) {
            Exercise exercise = detailedTopicWord.getQuiz().getExercise();
            if (exercise instanceof Dictation) {
                dictationList.add((Dictation) exercise);
            }
        }
        return dictationList;
    }
}
