package com.media2359.mediacorpspellinggame.data;

import android.content.Context;

import com.media2359.mediacorpspellinggame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xijunli on 13/2/17.
 */

public class DataRepo {

    private static DataRepo INSTANCE;

    private String schoolName;

    private List<Result> results;

    private int totalScore;

    private List<GameType> gameTypeList;

    private List<Question> questionList;

    private LoadQuestionsAsyncTask questionsAsyncTask;

    private DataRepo() {
        results = new ArrayList<>();
        totalScore = 0;
        gameTypeList = new ArrayList<>();

        //TODO change game type details
        gameTypeList.add(new GameType(0, R.drawable.ic_game_a, "GAME A", "Let's test your hearing"));
        gameTypeList.add(new GameType(1, R.drawable.ic_game_b, "GAME B", "Let's test your hearing"));
        gameTypeList.add(new GameType(2, R.drawable.ic_game_b, "GAME C", "Let's test your hearing"));
    }

    public static DataRepo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataRepo();
        }
        return INSTANCE;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void increaseScore(int increment) {
        totalScore = totalScore + increment;
    }

    public void addResult(Result result) {
        results.add(result);
    }

    // save the results to a email
    public void save() {

    }

    public synchronized void loadQuestions(Context context, QuestionCallback callback) {
        if (questionList == null) {
            forceLoad(context, callback);
        } else {
            if (callback != null)
                callback.onQuestionsLoadingFinished(questionList);
        }
    }

    public Question getQuestion(int id) {
        if (questionList != null)
            return questionList.get(id);

        return null;
    }

    private void forceLoad(Context context, final QuestionCallback callback) {
        if (questionsAsyncTask != null) {
            questionsAsyncTask.cancel(true);
        }

        questionsAsyncTask = new LoadQuestionsAsyncTask(new LoadQuestionsAsyncTask.AsyncTaskCallback() {
            @Override
            public void onFinished(List<Question> questions) {
                questionList = questions;

                if (callback != null)
                    callback.onQuestionsLoadingFinished(questionList);
            }
        });

        questionsAsyncTask.execute(context.getResources().openRawResource(R.raw.questions));
    }

    public GameType getGameType(int index) {
        return gameTypeList.get(index);
    }

    public void onDestroy() {
        if (questionsAsyncTask != null) {
            questionsAsyncTask.cancel(true);
        }

        questionList = null;
    }

    public interface QuestionCallback {

        void onQuestionsLoadingFinished(List<Question> questions);

    }
}
