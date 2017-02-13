package com.media2359.mediacorpspellinggame.data;

import android.content.AsyncTaskLoader;
import android.content.Context;

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

public class JsonQuestionAsyncLoader extends AsyncTaskLoader<List<Question>> {

    private List<Question> questions;

    public JsonQuestionAsyncLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (questions != null) {
            // Use cached data
            deliverResult(questions);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }
    }

    @Override
    public List<Question> loadInBackground() {

        InputStream input = getContext().getResources().openRawResource(R.raw.questions);

        List<Question> data = new ArrayList<>();

        try {
            Type collectionType = new TypeToken<List<Question>>() {
            }.getType();

            Reader reader = new InputStreamReader(input, "UTF-8");

            data = Injection.getGson().fromJson(reader, collectionType);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public void deliverResult(List<Question> data) {
        // We’ll save the data for later retrieval
        questions = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }
}
