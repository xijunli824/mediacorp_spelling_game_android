package com.media2359.mediacorpspellinggame.game;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.GameRepo;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.widget.MinutesClockView;
import com.media2359.mediacorpspellinggame.widget.SecondsClockView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 14/2/17.
 */

public class GridQuestionsFragment extends Fragment implements AnswerBoxAdapter.ResultListener {

    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;

    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;

    @BindView(R.id.clockView)
    MinutesClockView clockView;

    @BindView(R.id.tvResultInstruction)
    TextView tvResultInstruction;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btnEdit)
    Button btnEdit;

    @BindView(R.id.btnNext)
    Button btnNext;

    private AnswerBoxAdapter adapter;

    private List<Question> questionList;

    public static GridQuestionsFragment newInstance() {

        Bundle args = new Bundle();

        GridQuestionsFragment fragment = new GridQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid_questions, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {

        questionList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        adapter = new AnswerBoxAdapter(questionList);

        recyclerView.setAdapter(adapter);

        GameRepo.getInstance().loadQuestions(getActivity(), new GameRepo.QuestionCallback() {
            @Override
            public void onQuestionsLoadingFinished(List<Question> questions) {
                questionList = questions;
                adapter.refreshData(questionList);
            }
        });

        tvResultInstruction.setText("Fill in the the words as shown on the picture.");
        tvQuestionCount.setText("QUESTION: 1/4");
        tvCurrentScore.setText(String.valueOf(GameRepo.getInstance().getTotalScore()));

        adapter.setResultListener(this);

        showSubmitButton();

        showEditButton();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clockView.resume();

        clockView.setTimeListener(new SecondsClockView.TimeListener() {
            @Override
            public void onSecond(long seconds) {
                if (seconds == 15)
                    clockView.showAlertClock();

                if (seconds >= 20){
                    clockView.pause();
                    adapter.checkAnswers();
                }
            }
        });
    }

    @Override
    public void onResultSubmit(int correctAnswers, int totalQuestions, int totalScore) {

        Snackbar.make(getView(), "Result is: " + correctAnswers + "/" + totalQuestions + "/" + totalScore, Snackbar.LENGTH_INDEFINITE).show();

        showNextButton();

        tvCurrentScore.setText(String.valueOf(GameRepo.getInstance().getTotalScore() + totalScore));
    }

    private void showSubmitButton() {
        btnNext.setText("Submit");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockView.pause();
                adapter.checkAnswers();
            }
        });
    }

    private void showNextButton() {
        btnNext.setText("Next");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showEditButton() {
        btnEdit.setText("EDIT");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.enableEditMode(true);
                showDoneButton();
            }
        });
    }

    private void showDoneButton() {
        btnEdit.setText("DONE");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.enableEditMode(false);
                adapter.checkAnswers();
                showEditButton();
            }
        });
    }
}
