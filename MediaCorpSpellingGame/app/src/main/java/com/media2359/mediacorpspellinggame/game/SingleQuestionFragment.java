package com.media2359.mediacorpspellinggame.game;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.GameRepo;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.widget.AnswerBox;
import com.media2359.mediacorpspellinggame.widget.SecondsClockView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 13/2/17.
 */

public class SingleQuestionFragment extends Fragment implements AnswerBox.AnswerListener{

    private static final String ARGS_QUESTION_ENTITY = "args_question";

    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;

    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;

    @BindView(R.id.clockView)
    SecondsClockView clockView;

    @BindView(R.id.tvResultInstruction)
    TextView tvResultInstruction;

    @BindView(R.id.answerBox)
    AnswerBox answerBox;

    @BindView(R.id.btnNext)
    Button btnNext;


    public static SingleQuestionFragment newInstance() {

        Bundle args = new Bundle();

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
                    answerBox.checkAnswer(new Question(0, "Apple", "Apple", 10));
                }
            }
        });

        tvResultInstruction.setText("Enter the word that was read to you.");

        tvCurrentScore.setText(String.valueOf(GameRepo.getInstance().getTotalScore()));

        btnNext.setText("Submit");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockView.pause();
                answerBox.checkAnswer(new Question(0, "Apple", "Apple", 10));
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

        clockView.resume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockView.pause();
        answerBox.setAnswerListener(null);
    }

    @Override
    public void onError(int time) {
        tvResultInstruction.setText(getString(R.string.game_a_error));
        showNextButton();
    }

    @Override
    public void onCorrect(int score) {
        tvResultInstruction.setText(getString(R.string.game_a_correct));
        GameRepo.getInstance().increaseScore(10);
        tvCurrentScore.setText(String.valueOf(GameRepo.getInstance().getTotalScore()));
        showNextButton();
    }

    private void showNextButton() {
        btnNext.setText("Next");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "On Next Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
