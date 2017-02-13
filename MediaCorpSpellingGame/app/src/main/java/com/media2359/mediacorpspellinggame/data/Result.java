package com.media2359.mediacorpspellinggame.data;

/**
 * Created by xijunli on 13/2/17.
 */

public class Result {

    private int id;

    private int questionId;

    private String theAnswer;

    private int score;

    private int timeTaken;

    public Result(Builder builder) {
        this.id = builder.id;
        this.questionId = builder.questionId;
        this.theAnswer = builder.theAnswer;
        this.score = builder.score;
        this.timeTaken = builder.timeTaken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTheAnswer() {
        return theAnswer;
    }

    public void setTheAnswer(String theAnswer) {
        this.theAnswer = theAnswer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public static class Builder {

        private int id;

        private int questionId;

        private String theAnswer;

        private int score;

        private int timeTaken;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder questionId(int questionId) {
            this.questionId =  questionId;
            return this;
        }

        public Builder theAnswer(String theAnswer) {
            this.theAnswer =  theAnswer;
            return this;
        }

        public Builder score(int score) {
            this.score =  score;
            return this;
        }

        public Builder timeTaken(int timeTaken) {
            this.timeTaken =  timeTaken;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }
}
