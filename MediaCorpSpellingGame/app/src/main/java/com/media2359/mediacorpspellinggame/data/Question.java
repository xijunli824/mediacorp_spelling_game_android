package com.media2359.mediacorpspellinggame.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xijunli on 13/2/17.
 */

public final class Question implements Parcelable {

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    private int id;
    @SerializedName("question")
    private String theQuestion;
    @SerializedName("answer")
    private String correctAnswer;
    private int score;
    private String instruction;

    public Question(int id, String theQuestion, String correctAnswer, int score, String instruction) {
        this.id = id;
        this.theQuestion = theQuestion;
        this.correctAnswer = correctAnswer;
        this.score = score;
        this.instruction = instruction;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        theQuestion = in.readString();
        correctAnswer = in.readString();
        score = in.readInt();
        instruction = in.readString();
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

    public String getInstruction() {
        return instruction;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Question) {
            Question right = (Question) obj;
            return (getId() == right.getId());
        }

        return false;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Q: " + theQuestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(theQuestion);
        dest.writeString(correctAnswer);
        dest.writeInt(score);
        dest.writeString(instruction);
    }
}
