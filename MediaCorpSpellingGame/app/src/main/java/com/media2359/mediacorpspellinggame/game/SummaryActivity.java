package com.media2359.mediacorpspellinggame.game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.base.BaseActivity;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.start.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 17/2/17.
 */

public class SummaryActivity extends BaseActivity {

    @BindView(R.id.tvCardScore)
    TextView tvCardScore;

    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;

    @BindView(R.id.tvCardTime)
    TextView tvCardTime;

    @BindView(R.id.btnComplete)
    Button btnComplete;

    @BindView(R.id.btnSendEmail)
    Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_result_summary);
        ButterKnife.bind(this);

        bindView();

        // Save the game record
        GameProgressManager.getInstance().saveCurrentGame();
    }

    private void bindView() {

        tvSchoolName.setText(GameProgressManager.getInstance().getSchoolName());
        tvCardScore.setText(GameProgressManager.getInstance().getTotalScoreString());
        tvCardTime.setText(GameProgressManager.getInstance().getTimeTakenString());

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity.startNewGame(SummaryActivity.this);
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameProgressManager.getInstance().sendCurrentGameProgressAsEmail(SummaryActivity.this);
            }
        });
    }


}
