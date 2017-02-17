package com.media2359.mediacorpspellinggame.data;

/**
 * Created by xijunli on 16/2/17.
 */

public class QuestionItem {

    private Question question;

    private String actualAnswer;

    private boolean isCorrect;

    public QuestionItem(Question question) {
        this.question = question;
        actualAnswer = "";
        isCorrect = false;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getActualAnswer() {
        return actualAnswer;
    }

    public void setActualAnswer(String actualAnswer) {
        this.actualAnswer = actualAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
