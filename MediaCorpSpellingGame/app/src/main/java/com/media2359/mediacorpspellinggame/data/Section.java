package com.media2359.mediacorpspellinggame.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xijunli on 15/2/17.
 */

public final class Section implements Parcelable {

    // the type of the game, can be A, B, C, D, or E
    private String type;

    // unique id of the game
    private int gameId;

    @SerializedName("questionList")
    private int[] questionIdList;

    private int questionCount;

    private String gameInstruction;

    private String questionInstruction;

    public Section(String type, int gameId, int[] questionIdList, int questionCount, String gameInstruction, String questionInstruction) {
        this.type = type;
        this.gameId = gameId;
        this.questionIdList = questionIdList;
        this.questionCount = questionCount;
        this.gameInstruction = gameInstruction;
        this.questionInstruction = questionInstruction;
    }


    protected Section(Parcel in) {
        type = in.readString();
        gameId = in.readInt();
        questionIdList = in.createIntArray();
        questionCount = in.readInt();
        gameInstruction = in.readString();
        questionInstruction = in.readString();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    public String getType() {
        return type;
    }

    public int getGameId() {
        return gameId;
    }

    public int[] getQuestionIdList() {
        return questionIdList;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public String getGameInstruction() {
        return gameInstruction;
    }

    public String getQuestionInstruction() {
        return questionInstruction;
    }

    @Override
    public int hashCode() {
        return gameId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Section) {
            Section right = (Section) obj;
            return (getGameId() == right.getGameId());
        }

        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(gameId);
        dest.writeIntArray(questionIdList);
        dest.writeInt(questionCount);
        dest.writeString(gameInstruction);
        dest.writeString(questionInstruction);
    }
}
