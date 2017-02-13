package com.media2359.mediacorpspellinggame.injection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by xijunli on 13/2/17.
 */

public class Injection {

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null){
            gson = new GsonBuilder().create();
        }
        return gson;
    }
}
