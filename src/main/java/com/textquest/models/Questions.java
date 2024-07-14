package com.textquest.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
public class Questions {
    private List<Question> questions = new ArrayList<>();

    public Questions(List<Question> questions) {
        this.questions = questions;
    }

    public Questions() {
    }

    public void questionsInitializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(JsonParser.class.getClassLoader().getResource("questions.json").getFile());
            questions = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading questions file", e);
        }
    }

    public String getNextQuestion(String question, String answer) {
        if (isNull(answer) || answer.isEmpty() || isNull(question) || question.isEmpty()) {
            return questions.get(0).getQuestion();
        }
        for (Question questionLoop : questions) {
            if (questionLoop.getQuestion().equals(question)) {
                return questionLoop.getAnswer().get(answer);
            }
        }
        return questions.get(0).getQuestion();
    }

    public String getFirstAnswer(String question) {
        return isNull(question) ? "" : getAnswers(question).get(0);
    }

    public String getSecondAnswer(String question) {
        return isNull(question) ? "" : getAnswers(question).get(1);
    }

    private List<String> getAnswers(String question) {
        List<String> answers = new ArrayList<>();
        for (Question questionLoop : questions) {
            if (questionLoop.getQuestion().equals(question)) {
                answers.addAll(questionLoop.getAnswer().keySet());
            }
        }
        return answers;
    }
}
