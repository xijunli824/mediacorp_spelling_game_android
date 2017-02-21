package com.media2359.mediacorpspellinggame.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Section;
import com.media2359.mediacorpspellinggame.data.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xijunli on 13/2/17.
 */

public class GameRepo {

    private static GameRepo INSTANCE;

    private List<Section> sectionList;

    private List<Question> questionList;

    private LoadQuestionsAsyncTask questionsAsyncTask;

    private LoadGamesAsyncTask gamesAsyncTask;

    private GameRepo() {
        sectionList = new ArrayList<>();
        questionList = new ArrayList<>();
    }

    public synchronized static GameRepo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameRepo();
        }
        return INSTANCE;
    }

    public synchronized void loadData(Context context, @NonNull GameDataCallback callback) {
        if (questionList.isEmpty() || sectionList.size() == 0) {
            forceLoad(context, callback);
        } else {
            callback.onLoadingFinished(sectionList, questionList);
        }
    }

    public Question getQuestion(int index) {
        if (index < 0 || index >= questionList.size())
            return null;

        if (questionList != null)
            return questionList.get(index);

        return null;
    }

    public List<Question> getListOfQuestionsFromGame(int gameIndex) {

        Section game = getSection(gameIndex);

        if (game == null)
            return null;

        List<Question> result = new ArrayList<>();

        for (int i:game.getQuestionIdList()){
            result.add(getQuestion(i));
        }

        return result;
    }

    public Section getSection(int index) {
        if (index < 0 || index >= sectionList.size())
            return null;

        return sectionList.get(index);
    }

    private void forceLoad(Context context, @NonNull final GameDataCallback gameDataCallback) {
        questionList.clear();
        sectionList.clear();

        if (questionsAsyncTask != null) {
            questionsAsyncTask.cancel(true);
        }

        questionsAsyncTask = new LoadQuestionsAsyncTask(new LoadQuestionsAsyncTask.AsyncTaskCallback() {
            @Override
            public void onFinished(List<Question> questions) {
                questionList = questions;
                reportBack(gameDataCallback);
            }
        });

        questionsAsyncTask.execute(context.getResources().openRawResource(R.raw.questions_set_a));

        if (gamesAsyncTask != null) {
            gamesAsyncTask.cancel(true);
        }

        gamesAsyncTask = new LoadGamesAsyncTask(new LoadGamesAsyncTask.AsyncTaskCallback() {
            @Override
            public void onFinished(List<Section> games) {
                sectionList = games;
                reportBack(gameDataCallback);
            }
        });

        gamesAsyncTask.execute(context.getResources().openRawResource(R.raw.games_set_a));
    }

    private void reportBack(@NonNull final GameDataCallback gameDataCallback) {
        if (isDataReady()){
            gameDataCallback.onLoadingFinished(sectionList, questionList);
        }
    }

    public boolean isDataReady() {
        return sectionList != null && sectionList.size() != 0 && questionList != null && !questionList.isEmpty();
    }

    public void onDestroy() {
        if (questionsAsyncTask != null) {
            questionsAsyncTask.cancel(true);
        }

        if (gamesAsyncTask != null) {
            gamesAsyncTask.cancel(true);
        }

        questionList = null;
        sectionList = null;
    }

    public interface GameDataCallback {

        void onLoadingFinished(List<Section> games, List<Question> questions);

    }
}
