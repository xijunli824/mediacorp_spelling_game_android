package com.media2359.mediacorpspellinggame.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.media2359.mediacorpspellinggame.BuildConfig;
import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.SpellingGameApplication;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.data.Section;

import java.io.InputStream;
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

    int currentDataSetId = -1;

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

    public void clearRepo() {
        if (sectionList != null)
            sectionList.clear();

        if (questionList != null)
            questionList.clear();
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

        for (int i : game.getQuestionIdList()) {
            result.add(getQuestion(i));
        }

        return result;
    }

    public Section getSection(int index) {
        if (index < 0 || index >= sectionList.size())
            return null;

        return sectionList.get(index);
    }

    public synchronized void loadData(int dataSetId, Context context, @NonNull GameDataCallback callback) {
//        if (questionList.isEmpty() || sectionList.size() == 0 || dataSetId != currentDataSetId) {
//            forceLoad(dataSetId, context, callback);
//        } else {
//            callback.onLoadingFinished(sectionList, questionList);
//        }
        forceLoad(dataSetId, context, callback);
    }

    private void forceLoad(int dataSetId, Context context, @NonNull final GameDataCallback gameDataCallback) {
        questionList.clear();
        sectionList.clear();

        currentDataSetId = dataSetId;

        InputStream gameSetInputStream, questionSetInputStream;

        switch (currentDataSetId){
            case 0:
                gameSetInputStream = context.getResources().openRawResource(R.raw.games_set_final);
                questionSetInputStream = context.getResources().openRawResource(R.raw.questions_set_a);
                break;
            case 1:
                gameSetInputStream = context.getResources().openRawResource(R.raw.games_set_a);
                questionSetInputStream = context.getResources().openRawResource(R.raw.questions_set_b);
                break;
            case 2:
                gameSetInputStream = context.getResources().openRawResource(R.raw.games_set_a);
                questionSetInputStream = context.getResources().openRawResource(R.raw.questions_set_c);
                break;
            case 3:
                gameSetInputStream = context.getResources().openRawResource(R.raw.games_set_a);
                questionSetInputStream = context.getResources().openRawResource(R.raw.questions_set_d);
                break;
//            case 4:
//                gameSetInputStream = context.getResources().openRawResource(R.raw.games_set_a);
//                questionSetInputStream = context.getResources().openRawResource(R.raw.questions_backup_set);
//                break;
            default:
                throw new IllegalArgumentException("Load data exception: wrong data set id");

        }

        if (BuildConfig.DEBUG){
            gameSetInputStream = context.getResources().openRawResource(R.raw.games_set_demo);
            questionSetInputStream = context.getResources().openRawResource(R.raw.questions_set_demo);
        }

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

        questionsAsyncTask.execute(questionSetInputStream);

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

        gamesAsyncTask.execute(gameSetInputStream);
    }

    private void reportBack(@NonNull final GameDataCallback gameDataCallback) {
        if (isDataReady()) {
            gameDataCallback.onLoadingFinished(sectionList, questionList);
        }
    }

    public boolean isDataReady() {
        return sectionList != null && sectionList.size() != 0 && questionList != null && !questionList.isEmpty();
    }

    private void loadDataIfNull(GameDataCallback gameDataCallback) {
        if (sectionList == null || sectionList.isEmpty() || questionList == null || questionList.isEmpty()){
            forceLoad(currentDataSetId, SpellingGameApplication.getContext(), gameDataCallback);
        }
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
