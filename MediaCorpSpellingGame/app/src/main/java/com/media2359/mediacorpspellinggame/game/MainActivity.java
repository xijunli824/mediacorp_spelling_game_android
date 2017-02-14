package com.media2359.mediacorpspellinggame.game;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.GameRepo;
import com.media2359.mediacorpspellinggame.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Fragment fragment = getFragmentManager()
                .findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = GridQuestionsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getFragmentManager(), fragment, R.id.container, false, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameRepo.getInstance().onDestroy();
    }
}
