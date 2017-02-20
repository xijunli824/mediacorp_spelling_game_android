package com.media2359.mediacorpspellinggame.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("answers")
    private List<String> correctAnswers;
    private int score;
    @SerializedName("additions")
    private List<String> additions;

    public Question(int id, String theQuestion, List<String> correctAnswers, int score, List<String> additions) {
        this.id = id;
        this.theQuestion = theQuestion;
        this.correctAnswers = correctAnswers;
        this.score = score;
        this.additions = additions;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        theQuestion = in.readString();
        in.readStringList(correctAnswers);
        score = in.readInt();
        in.readStringList(additions);
    }

    public int getId() {
        return id;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public int getScore() {
        return score;
    }


    public List<String> getAdditions() {
        return additions;
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
        dest.writeStringList(correctAnswers);
        dest.writeInt(score);
        dest.writeStringList(additions);
    }

}
