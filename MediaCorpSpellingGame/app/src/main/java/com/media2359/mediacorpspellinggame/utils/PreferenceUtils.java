package com.media2359.mediacorpspellinggame.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.media2359.mediacorpspellinggame.SpellingGameApplication;

/**
 * Created by xijunli on 15/2/17.
 */

public class PreferenceUtils {

    private static final String PREF_FILE_SAVE_DATA = "file_save_data";

    private static final String KEY_SCHOOL_NAME = "key_school_name";
    private static final String KEY_CURRENT_SCORE = "key_current_score";
    private static final String KEY_TIME_TAKEN = "key_time_taken";
    private static final String KEY_LAST_ATTEMPTED_GAME = "key_last_game";
    private static final String KEY_LAST_ATTEMPTED_QUESTION = "key_last_question";
    private static final String KEY_SESSION_ID = "key_session_id";

    public static SharedPreferences getSharedPreferences() {
        return SpellingGameApplication.getContext().getSharedPreferences(PREF_FILE_SAVE_DATA, Context.MODE_PRIVATE);
    }

    public static void saveSchoolName(String schoolName) {
        getSharedPreferences().edit().putString(KEY_SCHOOL_NAME, schoolName).apply();
    }

    public static String getSchoolName() {
        return getSharedPreferences().getString(KEY_SCHOOL_NAME, "");
    }

    public static void saveCurrentScore(int score) {
        getSharedPreferences().edit().putInt(KEY_CURRENT_SCORE, score).apply();
    }

    public static int getCurrentScore() {
        return getSharedPreferences().getInt(KEY_CURRENT_SCORE, 0);
    }

    public static void saveTimeTaken(int seconds) {
        getSharedPreferences().edit().putInt(KEY_TIME_TAKEN, seconds).apply();
    }

    public static int getTimeTaken() {
        return getSharedPreferences().getInt(KEY_TIME_TAKEN, 0);
    }

    public static void saveLastGameId(int gameId) {
        getSharedPreferences().edit().putInt(KEY_LAST_ATTEMPTED_GAME, gameId).apply();
    }

    public static int getLastGameId() {
        return getSharedPreferences().getInt(KEY_LAST_ATTEMPTED_GAME, -1);
    }

    public static void saveLastQuestionId(int questionId) {
        getSharedPreferences().edit().putInt(KEY_LAST_ATTEMPTED_QUESTION, questionId).apply();
    }

    public static int getLastQuestionId() {
        return getSharedPreferences().getInt(KEY_LAST_ATTEMPTED_QUESTION, -1);
    }

    public static void saveSessionId(String sessionId) {
        getSharedPreferences().edit().putString(KEY_SESSION_ID, sessionId).apply();
    }

    public static String getSessionId() {
        return getSharedPreferences().getString(KEY_SESSION_ID, "");
    }

}
