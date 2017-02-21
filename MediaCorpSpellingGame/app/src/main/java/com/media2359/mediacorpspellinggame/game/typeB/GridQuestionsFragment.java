package com.media2359.mediacorpspellinggame.game.typeB;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.factory.GameRepo;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;
import com.media2359.mediacorpspellinggame.widget.MinutesClockView;
import com.media2359.mediacorpspellinggame.widget.PasswordDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 14/2/17.
 */

public class GridQuestionsFragment extends Fragment implements AnswerBoxAdapter.ResultListener {

    private static final String ARGS_GAME_INDEX = "game_index";

    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;

    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;

    @BindView(R.id.tvCurrentScoreText)
    TextView tvCurrentScoreText;

    @BindView(R.id.clockView)
    MinutesClockView clockView;

    @BindView(R.id.tvResultInstruction)
    TextView tvResultInstruction;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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

    @BindView(R.id.btnEdit)
    Button btnEdit;

    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private AnswerBoxAdapter adapter;

    private List<Question> questionList;

    private int initialScore;

    private int sectionScore;

    public static GridQuestionsFragment newInstance(int gameIndex) {

        Bundle args = new Bundle();
        args.putInt(ARGS_GAME_INDEX, gameIndex);
        GridQuestionsFragment fragment = new GridQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialScore = GameProgressManager.getInstance().getTotalScore();
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
        recyclerView.setItemViewCacheSize(10);
        adapter = new AnswerBoxAdapter(questionList);

        recyclerView.setAdapter(adapter);

        int gameIndex = getArguments().getInt(ARGS_GAME_INDEX);

        questionList.addAll(GameRepo.getInstance().getListOfQuestionsFromGame(gameIndex));
        adapter.refreshData(questionList);

        tvResultInstruction.setText("Fill in the the words as shown on the picture.");
        tvQuestionCount.setText("QUESTION: 1/1");

        tvCurrentScore.setText(String.valueOf(GameProgressManager.getInstance().getTotalScore()));

        adapter.setResultListener(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClick();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordDialog();
            }
        });

        btnEdit.setVisibility(View.INVISIBLE);

        String gameType = ((GameActivity) getActivity()).getCurrentGame().getType();

        String sectionScoreText = "SECTION " + gameType + " SCORE";
        String sectionTimeText = "SECTION " + gameType + " TIME";

        tvSectionScoreText.setText(sectionScoreText);
        tvSectionTimeText.setText(sectionTimeText);

        showPlayView();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clockView.resume();

        clockView.setTimeListener(new MinutesClockView.TimeListener() {
            @Override
            public void onSecond(long seconds) {
                if (seconds == 165)
                    clockView.showAlertClock();

                if (seconds >= 20) {
                    onTimeExpired();
                }
            }
        });
    }

    @Override
    public void onResultSubmit(int correctAnswers, int totalQuestions, int totalScore) {

        sectionScore = totalScore;

        int gameId = ((GameActivity) getActivity()).getCurrentGame().getGameId();

        tvCardScore.setText(String.valueOf(totalScore));
        tvCardTime.setText(GameProgressManager.getInstance().getSectionTimeText(gameId));

        tvResultInstruction.setText("You have answered " + correctAnswers + "/" + totalQuestions + " questions correctly");

        showScoreView();
    }

    private void showPlayView() {

        clockView.setVisibility(View.VISIBLE);
        flScoreCard.setVisibility(View.INVISIBLE);
        tvCurrentScore.setVisibility(View.VISIBLE);
        tvCurrentScoreText.setVisibility(View.VISIBLE);

        btnSubmit.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
    }

    private void showScoreView() {

        clockView.setVisibility(View.INVISIBLE);
        flScoreCard.setVisibility(View.VISIBLE);
        tvCurrentScore.setVisibility(View.INVISIBLE);
        tvCurrentScoreText.setVisibility(View.INVISIBLE);

        btnSubmit.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        btnEdit.setEnabled(true);

        btnEdit.setVisibility(View.VISIBLE);

        btnNext.setText("Next");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishThisGame();
            }
        });

    }

    private void finishThisGame() {

        GameProgressManager.getInstance().increaseSectionScore(getActivity(), sectionScore);

        ((GameActivity) getActivity()).showGameSummaryPage();
    }

    private void onSubmitButtonClick() {
        clockView.pause();

        int timeSpent = (int) clockView.getElapsedTime();

        GameProgressManager.getInstance().increaseSectionTime(getActivity(), timeSpent);
        GameProgressManager.getInstance().increaseTime(timeSpent);

        CommonUtils.makeHoldOnAlertDialog(getActivity(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.reset();

                for (int i = 0; i < adapter.getItemCount(); i++) {
                    AnswerBoxAdapter.AnswerBoxViewHolder viewHolder = (AnswerBoxAdapter.AnswerBoxViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    viewHolder.checkAnswer(questionList.get(i));
                }

            }
        }).show();
    }

    private void onTimeExpired() {
        clockView.pause();

        int timeSpent = (int) clockView.getElapsedTime();

        GameProgressManager.getInstance().increaseSectionTime(getActivity(), timeSpent);
        GameProgressManager.getInstance().increaseTime(timeSpent);

        adapter.reset();

        for (int i = 0; i < adapter.getItemCount(); i++) {
            AnswerBoxAdapter.AnswerBoxViewHolder viewHolder = (AnswerBoxAdapter.AnswerBoxViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            viewHolder.checkAnswer(questionList.get(i));
        }
    }

    private void showPasswordDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PasswordDialogFragment newFragment = PasswordDialogFragment.newInstance();

        newFragment.setListener(new PasswordDialogFragment.PasswordListener() {
            @Override
            public void onPasswordMatch() {
                startEditing();
            }
        });

        newFragment.show(ft, "dialog");
    }

    private void startEditing() {

        adapter.enableEditMode(true);

        btnEdit.setEnabled(false);

        for (int i = 0; i < adapter.getItemCount(); i++) {
            AnswerBoxAdapter.AnswerBoxViewHolder viewHolder = (AnswerBoxAdapter.AnswerBoxViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            viewHolder.enableEditMode(true);
        }

        tvResultInstruction.setText("Edit Mode");
        showDoneButton();
    }

    private void showDoneButton() {
        btnNext.setText("DONE");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.enableEditMode(false);
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    AnswerBoxAdapter.AnswerBoxViewHolder viewHolder = (AnswerBoxAdapter.AnswerBoxViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    viewHolder.enableEditMode(false);
                    viewHolder.checkAnswer(questionList.get(i));
                }
            }
        });
    }
}
