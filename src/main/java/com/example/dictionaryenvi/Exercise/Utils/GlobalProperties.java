package com.example.dictionaryenvi.Exercise.Utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GlobalProperties {
    public static BooleanProperty globalShowingMultipleChoiceProperty = new SimpleBooleanProperty(false);
    public static BooleanProperty globalShowingDictationProperty = new SimpleBooleanProperty(false);
    public static BooleanProperty globalIsRunningExerciseProperty = new SimpleBooleanProperty(false);

    private static Runnable multipleChoiceCloseCallback;
    private static Runnable dictationCloseCallback;

    static {
        globalIsRunningExerciseProperty.addListener((observable, oldValue, newValue) -> {

        });

        globalShowingMultipleChoiceProperty.addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue && multipleChoiceCloseCallback != null && globalIsRunningExerciseProperty.get()) {
                multipleChoiceCloseCallback.run();
            }
        });

        globalShowingDictationProperty.addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue && dictationCloseCallback != null && globalIsRunningExerciseProperty.get()) {
                dictationCloseCallback.run();
            }
        });
    }

    public static void setMultipleChoiceCloseCallback(Runnable callback) {
        multipleChoiceCloseCallback = callback;
    }

    public static void setDictationCloseCallback(Runnable callback) {
        dictationCloseCallback = callback;
    }
}



