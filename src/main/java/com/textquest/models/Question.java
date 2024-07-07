package com.textquest.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Question {
    private String question;
    private Map<String, String> answer;

    public Question(String question, Map<String, String> answer) {
        this.question = question;
        this.answer = answer;
    }
}
