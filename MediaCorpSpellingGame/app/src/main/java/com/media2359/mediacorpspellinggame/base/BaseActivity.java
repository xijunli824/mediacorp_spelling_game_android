package com.media2359.mediacorpspellinggame.base;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xijunli on 15/2/17.
 */

public class BaseActivity extends Activity {

    @Override
    public void onBackPressed() {
        Log.d("BaseActivity", "onBackPressed: ");
        //super.onBackPressed();
    }
}
