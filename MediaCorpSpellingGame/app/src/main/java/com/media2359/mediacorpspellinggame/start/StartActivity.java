package com.media2359.mediacorpspellinggame.start;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.DataRepo;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.game.AnswerBox;
import com.media2359.mediacorpspellinggame.game.ClockView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 13/2/17.
 */

public class StartActivity extends Activity {

    @BindView(R.id.etSchoolName)
    EditText etSchoolName;

    @BindView(R.id.btnNext)
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
