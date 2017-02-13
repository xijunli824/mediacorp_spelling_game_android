package com.media2359.mediacorpspellinggame.data;

import android.os.AsyncTask;

import com.google.gson.reflect.TypeToken;
import com.media2359.mediacorpspellinggame.R;
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

public class LoadQuestionsAsyncTask extends AsyncTask<InputStream, Void, List<Question>> {

    protected AsyncTaskCallback callback;

    public LoadQuestionsAsyncTask(AsyncTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Question> doInBackground(InputStream... params) {

        InputStream input = params[0];

        List<Question> data = new ArrayList<>();

        try {
            Type collectionType = new TypeToken<List<Question>>(){}.getType();

            Reader reader = new InputStreamReader(input, "UTF-8");

            data = Injection.getGson().fromJson(reader, collectionType);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(List<Question> questions) {
        super.onPostExecute(questions);
        callback.onFinished(questions);

        callback = null;
    }

    public interface AsyncTaskCallback {
        void onFinished(List<Question> questions);
    }

}
