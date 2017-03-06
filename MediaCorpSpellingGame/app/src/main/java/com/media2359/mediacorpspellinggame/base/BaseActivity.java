package com.media2359.mediacorpspellinggame.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.data.Section;
import com.media2359.mediacorpspellinggame.factory.GameRepo;

import java.util.List;

/**
 * Created by xijunli on 15/2/17.
 */

public class BaseActivity extends Activity {

    @Override
    public void onBackPressed() {
        Log.d("BaseActivity", "onBackPressed: ");
        //super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!GameRepo.getInstance().isDataReady() && GameRepo.getInstance().getCurrentDataSetId() >= 0) {

            final ProgressDialog dialog = ProgressDialog.show(this, "Preparing...", "Please wait...", true, false);

            GameRepo.getInstance().loadDataIfNull(BaseActivity.this, new GameRepo.GameDataCallback() {
                @Override
                public void onLoadingFinished(List<Section> games, List<Question> questions) {
                    dialog.dismiss();
                }
            });
        }
    }
}
