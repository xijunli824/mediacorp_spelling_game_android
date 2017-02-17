package com.media2359.mediacorpspellinggame.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xijunli on 15/2/17.
 */

public final class Game implements Parcelable {

    // the type of the game, can be A, B, C, D, or E
    private String type;

    // unique id of the game
    private int gameId;

    @SerializedName("questionList")
    private int[] questionIdList;

    private int questionCount;

    private String gameInstruction;

    public Game(String type, int gameId, int[] questionIdList, int questionCount, String gameInstruction) {
        this.type = type;
        this.gameId = gameId;
        this.questionIdList = questionIdList;
        this.questionCount = questionCount;
        this.gameInstruction = gameInstruction;
    }


    protected Game(Parcel in) {
        type = in.readString();
        gameId = in.readInt();
        questionIdList = in.createIntArray();
        questionCount = in.readInt();
        gameInstruction = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
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

    @Override
    public int hashCode() {
        return gameId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Game) {
            Game right = (Game) obj;
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
    }
}
