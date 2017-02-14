package com.media2359.mediacorpspellinggame.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.GameRepo;
import com.media2359.mediacorpspellinggame.game.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        if (validateSchoolName()){
            GameRepo.getInstance().setSchoolName(etSchoolName.getText().toString().trim());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateSchoolName() {
        String schoolName = etSchoolName.getText().toString();

        if (TextUtils.isEmpty(schoolName) || schoolName.length() < 2){
            etSchoolName.setError("Please enter your school name here");
            return false;
        }

        return true;
    }
}
