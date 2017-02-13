package com.media2359.mediacorpspellinggame.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xijunli on 13/2/17.
 */

public final class Question {

    private int id;

    @SerializedName("question")
    private String theQuestion;

    @SerializedName("answer")
    private String correctAnswer;

    private int score;

    private String type;

    @SerializedName("picture")
    private List<String> pictureQuestion;

    public Question(int id, String theQuestion, String correctAnswer, int score) {
        this.id = id;
        this.theQuestion = theQuestion;
        this.correctAnswer = correctAnswer;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getScore() {
        return score;
    }
}
