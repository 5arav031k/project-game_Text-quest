package com.textquest.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QuestionsTest {
    @Spy
    public Questions questions;
    public static List<Question> questionsList;

    @BeforeAll
    static void setUp() {
        questionsList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            String answer1 = "Question%d.answer1".formatted(i);
            String answer2 = "Question%d.answer2".formatted(i);

            String nextQuestion1 = "Question%d".formatted(i*2);
            String nextQuestion2 = "Question%d".formatted(i*2+1);

            Map<String, String> answers = new TreeMap<>();
            answers.put(answer1, nextQuestion1);
            answers.put(answer2, nextQuestion2);

            questionsList.add(new Question("Question%d".formatted(i), answers));
        }
    }

    @BeforeEach
    void init() {
        questions = new Questions(questionsList);
    }

    @ParameterizedTest
    @CsvSource({
            "Question1, Question1.answer1",
            "Question1, Question1.answer2",
            "Question2, Question2.answer1",
            "Question2, Question2.answer2",
            "Question3, Question3.answer1",
            "Question3, Question3.answer2",
            "Question4, Question4.answer1",
            "Question4, Question4.answer2",
            "Question5, Question5.answer1",
            "Question5, Question5.answer2",
    })
    void getNextQuestion(String question, String answer) {
        int questionIndex = Integer.parseInt(question.charAt(question.length()-1)+"");
        int answerIndex = Integer.parseInt(answer.charAt(answer.length()-1)+"");

        String expectedQuestion = "Question%d".formatted(questionIndex*2+answerIndex-1);
        String actualQuestion = questions.getNextQuestion(question, answer);

        assertEquals(expectedQuestion, actualQuestion);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Question1", "Question2", "Question3", "Question4", "Question5", "Question6", "Question7", "Question8", "Question9"})
    void getAnswers(String question) {
        int index = Integer.parseInt(question.charAt(question.length()-1)+"");
        List<String> expectedAnswers = Arrays.asList("Question"+index+".answer1", "Question"+index+".answer2");

        for (Method method : questions.getClass().getDeclaredMethods()) {
            if (method.getName().equals("getAnswers")) {
                method.setAccessible(true);
                try {
                    @SuppressWarnings("unchecked")
                    List<String> actualAnswers = (List<String>) method.invoke(questions, question);
                    assertEquals(expectedAnswers, actualAnswers);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    System.out.println("Error while invoking method " + method.getName());
                }
            }
        }
    }
}