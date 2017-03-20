package com.media2359.mediacorpspellinggame.game.typeE;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;
import com.media2359.mediacorpspellinggame.widget.PasswordDialogFragment;
import com.media2359.mediacorpspellinggame.widget.SecondsClockView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xijunli on 13/2/17.
 */

public class TypeEGameFragment extends Fragment {

    private static final int QUESTION_SCORE = 10;

    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;

    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;

    @BindView(R.id.tvCurrentScoreText)
    TextView tvCurrentScoreText;

    @BindView(R.id.clockView)
    SecondsClockView clockView;

    @BindView(R.id.tvResultInstruction)
    TextView tvResultInstruction;

    @BindView(R.id.etAnswer)
    EditText etAnswer;

    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.flScoreCard)
    FrameLayout flScoreCard;

    @BindView(R.id.tvCardScore)
    TextView tvCardScore;

    @BindView(R.id.tvCardTime)
    TextView tvCardTime;

    @BindView(R.id.tvSectionScoreText)
    TextView tvSectionScoreText;

    @BindView(R.id.tvSectionTimeText)
    TextView tvSectionTimeText;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.logo)
    ImageView logo;

    @BindView(R.id.btnEdit)
    Button btnEdit;

    @BindView(R.id.btnCorrect)
    ImageButton btnCorrect;

    @BindView(R.id.btnWrong)
    ImageButton btnWrong;

    private boolean gameHasEnded = false;

    //private boolean scoreHasBeenGiven = false;

    int timeTaken;

    int MAX_TIME;

    int sectionScore;

    AlertDialog alertDialog;

    public static TypeEGameFragment newInstance() {
        Bundle args = new Bundle();
        TypeEGameFragment fragment = new TypeEGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MAX_TIME = ((GameActivity) getActivity()).getCurrentSectionTime();
        timeTaken = MAX_TIME;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game_5, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private void initViews() {

        clockView.setTimeListener(new SecondsClockView.TimeListener() {
            @Override
            public void onSecond(long seconds) {
                if (seconds == MAX_TIME - 8)
                    clockView.showAlertClock();

                if (seconds >= MAX_TIME) {
                    onTimeExpired();
                }
            }
        });

        tvResultInstruction.setVisibility(View.INVISIBLE);

        tvCurrentScore.setText(String.valueOf(GameProgressManager.getInstance().getTotalScore()));

        int qid = GameProgressManager.getInstance().getLastAttemptedQuestionPos() + 1;

        String questionCount = CommonUtils.getQuestionCountString(getActivity(), qid, ((GameActivity) getActivity()).getCurrentGame().getQuestionCount());
        tvQuestionCount.setText(questionCount);

        String gameType = ((GameActivity) getActivity()).getCurrentGame().getType();
        String sectionScoreText = "அங்கம் " + gameType + " - புள்ளிகள்";
        String sectionTimeText = "அங்கம் " + gameType + " - நேரம்";

        tvSectionScoreText.setText(sectionScoreText);
        tvSectionTimeText.setText(sectionTimeText);

        showPlayView();
    }

    private void onTimeExpired() {
        clockView.pause();
        //if (!scoreHasBeenGiven){
            markAsError();
        //}
        gameHasEnded = true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAnswer.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAnswer, InputMethodManager.SHOW_IMPLICIT);

        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!gameHasEnded)
            clockView.resume();
    }

    private void showJudgeView() {

        btnSubmit.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);

        btnCorrect.setClickable(true);
        btnWrong.setClickable(true);

        btnCorrect.setVisibility(View.VISIBLE);
        btnWrong.setVisibility(View.VISIBLE);

        btnEdit.setVisibility(View.GONE);

        clockView.setVisibility(View.VISIBLE);
        flScoreCard.setVisibility(View.GONE);
        tvCurrentScore.setVisibility(View.VISIBLE);
        tvCurrentScoreText.setVisibility(View.VISIBLE);

        logo.setVisibility(View.VISIBLE);

    }

    private void showPlayView() {

        btnSubmit.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);

        btnCorrect.setVisibility(View.GONE);
        btnWrong.setVisibility(View.GONE);

        btnCorrect.setClickable(false);
        btnWrong.setClickable(false);

        btnEdit.setVisibility(View.GONE);

        clockView.setVisibility(View.VISIBLE);
        flScoreCard.setVisibility(View.GONE);
        tvCurrentScore.setVisibility(View.VISIBLE);
        tvCurrentScoreText.setVisibility(View.VISIBLE);

        logo.setVisibility(View.VISIBLE);

    }

    private void showScoreView() {

        //scoreHasBeenGiven = true;

        btnCorrect.setClickable(false);
        btnWrong.setClickable(false);

        etAnswer.setEnabled(false);

        clockView.setVisibility(View.GONE);
        flScoreCard.setVisibility(View.VISIBLE);
        tvCurrentScore.setVisibility(View.GONE);
        tvCurrentScoreText.setVisibility(View.GONE);

        int gameId = ((GameActivity) getActivity()).getCurrentGame().getGameId();

        String sectionScoreText = String.valueOf(GameProgressManager.getInstance().getSectionScore(gameId) + sectionScore);
        String sectionTimeText = String.valueOf(GameProgressManager.getInstance().getSectionTime(gameId) + timeTaken);

        tvCardScore.setText(sectionScoreText);
        tvCardTime.setText(sectionTimeText);

        btnSubmit.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        btnEdit.setVisibility(View.VISIBLE);

        logo.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btnWrong)
    public void onErrorClick() {

        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();

        alertDialog = CommonUtils.makeHoldOnAlertDialog(getActivity(), getString(R.string.game_e_mark_as_wrong), getString(R.string.game_e_proceed_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markAsError();
            }
        });

        alertDialog.show();
    }

    private void markAsError() {
        btnCorrect.setClickable(false);
        btnWrong.setClickable(false);

        tvResultInstruction.setVisibility(View.VISIBLE);
        tvResultInstruction.setText(getString(R.string.game_a_error));

        etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0);

        sectionScore = 0;

        btnCorrect.setVisibility(View.INVISIBLE);
        btnWrong.setVisibility(View.VISIBLE);

        showScoreView();
    }

    @OnClick(R.id.btnCorrect)
    public void onCorrect() {

        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();

        alertDialog = CommonUtils.makeHoldOnAlertDialog(getActivity(), getString(R.string.game_e_mark_as_correct), getString(R.string.game_e_proceed_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnCorrect.setClickable(false);
                btnWrong.setClickable(false);

                tvResultInstruction.setVisibility(View.VISIBLE);
                tvResultInstruction.setText(getString(R.string.game_a_correct));

                etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);

                sectionScore = QUESTION_SCORE;

                btnWrong.setVisibility(View.INVISIBLE);
                btnCorrect.setVisibility(View.VISIBLE);

                showScoreView();
            }
        });

        alertDialog.show();
    }

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        // update the section score/time
        GameProgressManager.getInstance().increaseSectionScore(getActivity(), sectionScore);
        GameProgressManager.getInstance().increaseSectionTime(getActivity(), timeTaken);

        ((GameActivity) getActivity()).showNextQuestion();
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {
        timeTaken = (int) clockView.getElapsedTime();
        //clockView.pauseAnimationOnly();
        clockView.pause();

        btnSubmit.setEnabled(false);
        etAnswer.setEnabled(false);

        gameHasEnded = true;

        showJudgeView();
    }

    @OnClick(R.id.btnEdit)
    public void onEditClick() {
        showPasswordDialog();
    }

    private void showPasswordDialog() {
        CommonUtils.showPasswordDialogFragment(getChildFragmentManager(), new PasswordDialogFragment.PasswordListener() {
            @Override
            public void onPasswordMatch() {
                showJudgeView();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!gameHasEnded)
            clockView.pauseAndSync();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockView.pause();
    }
}
