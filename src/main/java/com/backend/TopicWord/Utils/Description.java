package com.backend.TopicWord.Utils;

import com.backend.Exercise.Exercises.Dictation.DictationDescription;
import com.backend.Exercise.Exercises.MultipleChoice.MultipleChoiceDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceDescription.class, name = "MultipleChoice"),
        @JsonSubTypes.Type(value = DictationDescription.class, name = "Dictation")
})
public abstract class Description {
    public Description() {

    }
}
