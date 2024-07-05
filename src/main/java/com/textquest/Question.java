package com.textquest;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Question {
    private String question;
    private Map<String, String> answer;
}
