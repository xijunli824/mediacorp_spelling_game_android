package com.media2359.mediacorpspellinggame.start;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.base.BaseActivity;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.data.Section;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.factory.GameRepo;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;
import com.media2359.mediacorpspellinggame.widget.SessionSelectionFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xijunli on 13/2/17.
 */

public class StartActivity extends BaseActivity {

    @BindView(R.id.etSchoolName)
    EditText etSchoolName;

    @BindView(R.id.btnNext)
    Button btnNext;

    public static void startNewGame(Activity activity) {
        Intent intent = new Intent(activity, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        // Reset data
        GameProgressManager.getInstance().newGame();
        GameRepo.getInstance().clearRepo();

        selectSession();

        //prepareData(0);
    }

    private void selectSession() {
        Fragment fragment = getFragmentManager().findFragmentByTag("session");

        if (fragment == null) {

            SessionSelectionFragment dialogFragment = SessionSelectionFragment.newInstance();

            dialogFragment.setCancelable(false);

            dialogFragment.setCallback(new SessionSelectionFragment.SessionSelectCallback() {
                @Override
                public void onSessionSelected(int sessionId) {
                    prepareData(sessionId);
                }
            });

            CommonUtils.dismissKeyboard(etSchoolName);

            dialogFragment.show(getFragmentManager(), "session");
        }

    }

    private void prepareData(int sessionId) {

        final ProgressDialog dialog = ProgressDialog.show(this, "Preparing...", "Please wait...", true, false);

        GameRepo.getInstance().loadData(sessionId, this, new GameRepo.GameDataCallback() {
            @Override
            public void onLoadingFinished(List<Section> games, List<Question> questions) {
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        if (validateSchoolName()) {
            // save school name
            GameProgressManager.getInstance().setSchoolName(etSchoolName.getText().toString().trim());

            // start the first game
            Section firstGame = GameRepo.getInstance().getSection(4);
            GameActivity.startGameActivity(this, firstGame);

            // finish this activity
            finish();
        }
    }

    private boolean validateSchoolName() {
        String schoolName = etSchoolName.getText().toString();

        if (TextUtils.isEmpty(schoolName) || schoolName.length() < 2) {
            etSchoolName.setError("Please enter your school name here");
            return false;
        }

        return true;
    }
}
