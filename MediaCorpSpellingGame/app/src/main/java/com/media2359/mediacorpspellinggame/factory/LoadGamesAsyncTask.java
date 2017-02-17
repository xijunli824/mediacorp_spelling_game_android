package com.media2359.mediacorpspellinggame.factory;

import android.os.AsyncTask;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.media2359.mediacorpspellinggame.data.Game;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.injection.Injection;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xijunli on 13/2/17.
 */

public class LoadGamesAsyncTask extends AsyncTask<InputStream, Void, List<Game>> {

    protected AsyncTaskCallback callback;

    public LoadGamesAsyncTask(AsyncTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Game> doInBackground(InputStream... params) {

        InputStream input = params[0];

        List<Game> data = new ArrayList<>();

        try {
            Type collectionType = new TypeToken<List<Game>>(){}.getType();

            Reader reader = new InputStreamReader(input, "UTF-8");

            data = Injection.getGson().fromJson(reader, collectionType);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return data;
    }

    @Override
    protected void onPostExecute(List<Game> games) {
        super.onPostExecute(games);
        callback.onFinished(games);

        callback = null;
    }

    public interface AsyncTaskCallback {
        void onFinished(List<Game> games);
    }

}
