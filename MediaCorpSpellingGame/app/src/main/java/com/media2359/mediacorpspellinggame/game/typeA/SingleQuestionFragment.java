package com.media2359.mediacorpspellinggame.game.typeA;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;
import com.media2359.mediacorpspellinggame.widget.AnswerBox;
import com.media2359.mediacorpspellinggame.widget.SecondsClockView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 13/2/17.
 */

public class SingleQuestionFragment extends Fragment implements AnswerBox.AnswerListener {

    private static final String ARGS_QUESTION_ENTITY = "args_question";

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

    @BindView(R.id.answerBox)
    AnswerBox answerBox;

    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.flScoreCard)
    FrameLayout flScoreCard;

    @BindView(R.id.tvCardScore)
    TextView tvCardScore;

    @BindView(R.id.tvCardTime)
    TextView tvCardTime;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.logo)
    ImageView logo;

    private Question question;

    public static SingleQuestionFragment newInstance(@NonNull Question question) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_QUESTION_ENTITY, question);
        SingleQuestionFragment fragment = new SingleQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_single_question, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private void initViews() {

        answerBox.setAnswerListener(this);

        clockView.setTimeListener(new SecondsClockView.TimeListener() {
            @Override
            public void onSecond(long seconds) {
                if (seconds == 25)
                    clockView.showAlertClock();

                if (seconds >= 30) {
                    clockView.pause();
                    GameProgressManager.getInstance().increaseTime(30);
                    onError(30);
                }
            }
        });

        tvResultInstruction.setText(question.getInstruction());

        tvCurrentScore.setText(String.valueOf(GameProgressManager.getInstance().getTotalScore()));

        int qid = GameProgressManager.getInstance().getLastAttemptedQuestionPos() + 1;
        tvQuestionCount.setText("Question: " + qid + "/" + ((GameActivity) getActivity()).getCurrentGame().getQuestionCount());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockView.pause();

                CommonUtils.makeHoldOnAlertDialog(getActivity(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        answerBox.checkAnswer(question);
                    }
                }).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).showNextQuestion();
            }
        });

        showPlayView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clockView.resume();

        question = getArguments().getParcelable(ARGS_QUESTION_ENTITY);

        initViews();
    }

    private void showPlayView() {

        btnSubmit.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);

        clockView.setVisibility(View.VISIBLE);
        flScoreCard.setVisibility(View.GONE);
        tvCurrentScore.setVisibility(View.VISIBLE);
        tvCurrentScoreText.setVisibility(View.VISIBLE);

        logo.setVisibility(View.VISIBLE);

    }

    private void showScoreView() {

        clockView.setVisibility(View.GONE);
        flScoreCard.setVisibility(View.VISIBLE);
        tvCurrentScore.setVisibility(View.GONE);
        tvCurrentScoreText.setVisibility(View.GONE);

        tvCardScore.setText(GameProgressManager.getInstance().getTotalScoreString());
        tvCardTime.setText(GameProgressManager.getInstance().getTimeTakenString());

        btnSubmit.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        logo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(int time) {
        tvResultInstruction.setText(getString(R.string.game_a_error));

        GameProgressManager.getInstance().increaseTime(time);

        showScoreView();
    }

    @Override
    public void onCorrect(int score) {
        tvResultInstruction.setText(getString(R.string.game_a_correct));

        GameProgressManager.getInstance().increaseScore(question.getScore());

        GameProgressManager.getInstance().increaseTime((int) clockView.getElapsedTime());

        showScoreView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockView.pause();
        answerBox.setAnswerListener(null);
    }
}
