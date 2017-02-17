package com.media2359.mediacorpspellinggame.factory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.media2359.mediacorpspellinggame.data.Game;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.PreferenceUtils;

import java.util.IllegalFormatException;

/**
 * Created by xijunli on 15/2/17.
 */

public class GameProgressManager {

    private static GameProgressManager INSTANCE;

    private String schoolName;

    private int totalScore;

    private int timeTaken;

    private int lastAttemptedGamePos;

    private int lastAttemptedQuestionPos;

    private String sessionId;

    private GameProgressManager() {
        newGame();
    }

    public static GameProgressManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameProgressManager();
        }
        return INSTANCE;
    }

    public void newGame() {
        schoolName = "";
        totalScore = 0;
        timeTaken = 0;
        lastAttemptedGamePos = -1;
        lastAttemptedQuestionPos = -1;
        sessionId = "";
    }

    public void saveCurrentGame() {
        PreferenceUtils.saveCurrentScore(totalScore);
        PreferenceUtils.saveSchoolName(schoolName);
        PreferenceUtils.saveTimeTaken(timeTaken);
        PreferenceUtils.saveLastGameId(lastAttemptedGamePos);
        PreferenceUtils.saveLastQuestionId(lastAttemptedQuestionPos);
        PreferenceUtils.saveSessionId(sessionId);
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
        PreferenceUtils.saveSchoolName(schoolName);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getTotalScoreString() {
        return String.valueOf(totalScore);
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
        PreferenceUtils.saveCurrentScore(totalScore);
    }

    public void increaseScore(int increment) {
        this.totalScore += increment;
        PreferenceUtils.saveCurrentScore(totalScore);
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public String getTimeTakenString() {
        int minutes = timeTaken / 60;
        int seconds = timeTaken % 60;
        if (seconds < 10){
            return minutes + ":0" + seconds;
        }else {
            return minutes + ":" + seconds;
        }
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
        PreferenceUtils.saveTimeTaken(timeTaken);
    }

    public void increaseTime(int increment) {
        this.timeTaken += increment;
        PreferenceUtils.saveTimeTaken(timeTaken);
    }

    public int getLastAttemptedGamePos() {
        return lastAttemptedGamePos;
    }

    public void setLastAttemptedGamePos(int lastAttemptedGamePos) {
        this.lastAttemptedGamePos = lastAttemptedGamePos;
        PreferenceUtils.saveLastGameId(lastAttemptedGamePos);
    }

    public int getLastAttemptedQuestionPos() {
        return lastAttemptedQuestionPos;
    }

    public void setLastAttemptedQuestionPos(int lastAttemptedQuestionPos) {
        this.lastAttemptedQuestionPos = lastAttemptedQuestionPos;
        PreferenceUtils.saveLastQuestionId(lastAttemptedQuestionPos);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        PreferenceUtils.saveSessionId(sessionId);
    }

    public void sendCurrentGameProgressAsEmail(Context context) {
        String subjectText = "Game results for " + getSchoolName();

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("School Name: ").append(schoolName).append("\n")
                .append("Score: ").append(totalScore).append("\n")
                .append("Time: ").append(timeTaken).append("\n");

        String bodyText = bodyBuilder.toString();

        String[] addresses = {"xijun.li@2359media.com"};


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectText);
        intent.putExtra(Intent.EXTRA_TEXT, bodyText);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Error: No Email App Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void increaseSectionScore(Activity activity, int score){
        if (activity instanceof GameActivity){
            GameActivity gameActivity = (GameActivity) activity;
            int sectionScore = gameActivity.getSectionScore() + score;
            gameActivity.setSectionScore(sectionScore);
        }else {
            throw new IllegalArgumentException("Activity is not Game Activity");
        }
    }

    public void increaseSectionTime(Activity activity, int time){
        if (activity instanceof GameActivity){
            GameActivity gameActivity = (GameActivity) activity;
            int sectionTime = gameActivity.getSectionTime() + time;
            gameActivity.setSectionTime(sectionTime);
        }else {
            throw new IllegalArgumentException("Activity is not Game Activity");
        }
    }
}
