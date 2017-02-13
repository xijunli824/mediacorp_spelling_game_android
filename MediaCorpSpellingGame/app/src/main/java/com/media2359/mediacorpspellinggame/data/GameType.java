package com.media2359.mediacorpspellinggame.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

/**
 * Created by xijunli on 13/2/17.
 */

public final class GameType implements Parcelable{

    private int typeId;

    @DrawableRes
    private int iconResId;

    private String gameName;

    private String gameHint;

    public GameType(int typeId, int iconResId, String gameName, String gameHint) {
        this.typeId = typeId;
        this.iconResId = iconResId;
        this.gameName = gameName;
        this.gameHint = gameHint;
    }

    protected GameType(Parcel in) {
        typeId = in.readInt();
        iconResId = in.readInt();
        gameName = in.readString();
        gameHint = in.readString();
    }

    public static final Creator<GameType> CREATOR = new Creator<GameType>() {
        @Override
        public GameType createFromParcel(Parcel in) {
            return new GameType(in);
        }

        @Override
        public GameType[] newArray(int size) {
            return new GameType[size];
        }
    };

    public int getTypeId() {
        return typeId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameHint() {
        return gameHint;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(typeId);
        dest.writeInt(iconResId);
        dest.writeString(gameName);
        dest.writeString(gameHint);
    }
}
