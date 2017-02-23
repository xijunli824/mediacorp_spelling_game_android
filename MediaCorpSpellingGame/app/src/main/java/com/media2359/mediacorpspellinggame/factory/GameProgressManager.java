package com.media2359.mediacorpspellinggame.factory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;
import com.media2359.mediacorpspellinggame.utils.PreferenceUtils;

/**
 * Created by xijunli on 15/2/17.
 */

public class GameProgressManager {

    private static GameProgressManager INSTANCE;

    private String schoolName;

    //private int timeTaken;

    private int lastAttemptedGamePos;

    private int lastAttemptedQuestionPos;

    private String sessionId;

    private int[] sectionScores;

    private int[] sectionTimes;

    private String[] gameTypes = {"A", "B", "C", "D", "E"};

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
        //timeTaken = 0;
        lastAttemptedGamePos = -1;
        lastAttemptedQuestionPos = -1;
        sessionId = "";
        sectionScores = new int[]{-1, -1, -1, -1, -1}; // According to plan, there will be a maximum of 5 games
        sectionTimes = new int[]{-1, -1, -1, -1, -1};
    }

    public void saveCurrentGame() {
//        PreferenceUtils.saveCurrentScore(getTotalScore());
//        PreferenceUtils.saveSchoolName(schoolName);
//        PreferenceUtils.saveTimeTaken(timeTaken);
//        PreferenceUtils.saveLastGameId(lastAttemptedGamePos);
//        PreferenceUtils.saveLastQuestionId(lastAttemptedQuestionPos);
//        PreferenceUtils.saveSessionId(sessionId);

        StringBuilder builder = new StringBuilder();
        builder.append("School Name: ")
                .append(schoolName).append("\n")
                .append("Total Score: ")
                .append(getTotalScore()).append("\n")
                .append("Total Time Taken: ")
                .append(getTimeTaken()).append("\n")
                .append("Section records: ").append("\n");

        for (int i=0; i< sectionScores.length; i ++){
            if (sectionScores[i] >= 0 && sectionTimes[i] >= 0){
                builder.append("Section ").append(gameTypes[i]).append(" Score: ").append(sectionScores[i]).append("\n");
                builder.append("Section ").append(gameTypes[i]).append(" Time: ").append(sectionTimes[i]).append("\n");
            }
        }

        String record = builder.toString();

        PreferenceUtils.updateGameRecords(record);
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

        for (int i : sectionScores) {
            if (i >= 0){
                totalScore += i;
            }
        }

        return totalScore;
    }

    public String getTotalScoreString() {
        return String.valueOf(getTotalScore());
    }


    public int getTimeTaken() {
        int timeTaken = 0;

        for (int time: sectionTimes){
            if (time >= 0){
                timeTaken += time;
            }
        }

        return timeTaken;
    }

    public String getTimeTakenString() {
        return CommonUtils.convertSecondsIntegerToClockFormatString(getTimeTaken());
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

        String bodyText = PreferenceUtils.getPastRecords();

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

    public void increaseSectionScore(Activity activity, int score) {
        if (activity instanceof GameActivity) {
            GameActivity gameActivity = (GameActivity) activity;
            int gameId = gameActivity.getCurrentGame().getGameId();

            if (sectionScores[gameId] < 0){
                sectionScores[gameId] = 0;
            }

            int sectionScore = sectionScores[gameId] + score;
            sectionScores[gameId] = sectionScore;
        } else {
            throw new IllegalArgumentException("Activity is not Section Activity");
        }
    }

    public void increaseSectionTime(Activity activity, int time) {
        if (activity instanceof GameActivity) {
            GameActivity gameActivity = (GameActivity) activity;
            int gameId = gameActivity.getCurrentGame().getGameId();

            if (sectionTimes[gameId] < 0){
                sectionTimes[gameId] = 0;
            }

            int sectionTime = sectionTimes[gameId] + time;
            sectionTimes[gameId] = sectionTime;
        } else {
            throw new IllegalArgumentException("Activity is not Section Activity");
        }
    }

    public int getSectionScore(int sectionId) {
        if (sectionScores[sectionId] < 0){
            sectionScores[sectionId] = 0;
        }
        return sectionScores[sectionId];
    }

    public String getSectionScoreText(int sectionId) {
        return String.valueOf(getSectionScore(sectionId));
    }

    public int getSectionTime(int sectionId) {
        if (sectionTimes[sectionId] < 0){
            sectionTimes[sectionId] = 0;
        }
        return sectionTimes[sectionId];
    }

    public String getSectionTimeText(int sectionId) {
        return CommonUtils.convertSecondsIntegerToClockFormatString(getSectionTime(sectionId));
    }
}
