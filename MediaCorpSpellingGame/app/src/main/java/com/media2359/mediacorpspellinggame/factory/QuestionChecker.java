package com.media2359.mediacorpspellinggame.factory;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.media2359.mediacorpspellinggame.data.Question;

import java.util.List;

/**
 * Created by xijunli on 28/2/17.
 */

public class QuestionChecker {

    public static boolean isAnswerCorrect(@NonNull String answer, Question question) {
        if (answer == null)
            return false;

        // get the input
        String actualAnswer = answer.trim();
        List<String> correctAnswers = question.getCorrectAnswers();

        // if the answer is empty
        if (actualAnswer.isEmpty() || actualAnswer.length() <=0){
            return false;
        }

        // check if the answer is correct
        if (correctAnswers.contains(actualAnswer)){
            return true;
        }else {
            return false;
        }
    }
}
