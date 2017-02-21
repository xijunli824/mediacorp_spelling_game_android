package com.media2359.mediacorpspellinggame.factory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.PreferenceUtils;

/**
 * Created by xijunli on 15/2/17.
 */

public class GameProgressManager {

    private static GameProgressManager INSTANCE;

    private String schoolName;

    private int timeTaken;

    private int lastAttemptedGamePos;

    private int lastAttemptedQuestionPos;

    private String sessionId;

    private int[] sectionScores;

    private int[] sectionTimes;

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
        timeTaken = 0;
        lastAttemptedGamePos = -1;
        lastAttemptedQuestionPos = -1;
        sessionId = "";
        sectionScores = new int[] {0, 0, 0, 0, 0}; // According to plan, there will be a maximum of 5 games
        sectionTimes = new int[] {0, 0, 0, 0, 0};
    }

    public void saveCurrentGame() {
        PreferenceUtils.saveCurrentScore(getTotalScore());
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
        int totalScore = 0;

        for (int i:sectionScores){
            totalScore += i;
        }

        return totalScore;
    }

    public String getTotalScoreString() {
        return String.valueOf(getTotalScore());
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
        String subjectText = "Section results for " + getSchoolName();

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("School Name: ").append(schoolName).append("\n")
                .append("Score: ").append(getTotalScore()).append("\n")
                .append("Time: ").append(timeTaken).append("\n");

        String bodyText = bodyBuilder.toString();

        String[] addresses = {"dummyEmail@2359media.com"};


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
            int gameId = gameActivity.getCurrentGame().getGameId();
            int sectionScore = sectionScores[gameId] + score;
            sectionScores[gameId] = sectionScore;
        }else {
            throw new IllegalArgumentException("Activity is not Section Activity");
        }
    }

    public void increaseSectionTime(Activity activity, int time){
        if (activity instanceof GameActivity){
            GameActivity gameActivity = (GameActivity) activity;
            int gameId = gameActivity.getCurrentGame().getGameId();
            int sectionTime = sectionTimes[gameId] + time;
            sectionTimes[gameId] = sectionTime;
        }else {
            throw new IllegalArgumentException("Activity is not Section Activity");
        }
    }

    public int getSectionScore(int sectionId) {
        return sectionScores[sectionId];
    }

    public String getSectionScoreText(int sectionId) {
        return String.valueOf(sectionScores[sectionId]);
    }

    public int getSectionTime(int sectionId) {
        return sectionTimes[sectionId];
    }

    public String getSectionTimeText(int sectionId) {
        int sectionTime = sectionTimes[sectionId];

        int minutes = sectionTime / 60;
        int seconds = sectionTime % 60;
        if (seconds < 10){
            return minutes + ":0" + seconds;
        }else {
            return minutes + ":" + seconds;
        }
    }
}
