package com.media2359.mediacorpspellinggame;

import android.app.Application;
import android.content.Context;

/**
 * Created by xijunli on 13/2/17.
 */

public class SpellingGameApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
