package com.media2359.mediacorpspellinggame;

import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.factory.QuestionChecker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by xijunli on 28/2/17.
 */

@RunWith(JUnit4.class)
public class QuestionLogicUnitTest {

    private Question questionCorrect;

    private static String correctAnswer = "கிடங்கு";

    @Before
    public void prepareQuestionForTest() {
        List<String> answers = new ArrayList<>();
        answers.add(correctAnswer);

        questionCorrect = new Question(999, correctAnswer, answers, 10, null);
    }

    @Test
    public void checkAnswer_isCorrect() throws Exception {
        String answer = correctAnswer;
        boolean isCorrect = QuestionChecker.isAnswerCorrect(answer, questionCorrect);
        assertTrue(isCorrect);
    }

    @Test
    public void checkAnswer_isWrong() throws Exception {
        String answer = "கலக்கம்";
        boolean isCorrect = QuestionChecker.isAnswerCorrect(answer, questionCorrect);
        assertFalse(isCorrect);
    }

    @Test
    public void checkAnswer_emptyInput_isWrong() throws Exception {
        String answer = "";
        boolean isCorrect = QuestionChecker.isAnswerCorrect(answer, questionCorrect);
        assertFalse(isCorrect);
    }

    @Test
    public void checkAnswer_nullInput_isWrong() throws Exception {
        String answer = null;
        boolean isCorrect = QuestionChecker.isAnswerCorrect(answer, questionCorrect);
        assertFalse(isCorrect);
    }
}
