package com.media2359.mediacorpspellinggame.game.typeA;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import butterknife.OnClick;

/**
 * Created by xijunli on 13/2/17.
 */

public class TypeAGameFragment extends Fragment implements AnswerBox.AnswerListener {

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

    @BindView(R.id.tvSectionScoreText)
    TextView tvSectionScoreText;

    @BindView(R.id.tvSectionTimeText)
    TextView tvSectionTimeText;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.logo)
    ImageView logo;

    int timeTaken;

    int MAX_TIME;

    @NonNull
    private Question question;

    private boolean gameHasEnded = false;

    public static TypeAGameFragment newInstance(@NonNull Question question) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_QUESTION_ENTITY, question);
        TypeAGameFragment fragment = new TypeAGameFragment();
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
        View root = inflater.inflate(R.layout.fragment_game_1, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private void initViews() {

        answerBox.setAnswerListener(this);
        answerBox.setQuestion(question);

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

        //tvResultInstruction.setText(instruction);
        tvResultInstruction.setVisibility(View.INVISIBLE);

        tvCurrentScore.setText(String.valueOf(GameProgressManager.getInstance().getTotalScore()));

        int qid = GameProgressManager.getInstance().getLastAttemptedQuestionPos() + 1;

        String questionCount = CommonUtils.getQuestionCountString(getActivity(), qid, ((GameActivity) getActivity()).getCurrentGame().getQuestionCount());
        tvQuestionCount.setText(questionCount);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).showNextQuestion();
            }
        });

        String gameType = ((GameActivity) getActivity()).getCurrentGame().getType();
        String sectionScoreText = "அங்கம் " + gameType + " - புள்ளிகள்";
        String sectionTimeText = "அங்கம் " + gameType + " - நேரம்";

        tvSectionScoreText.setText(sectionScoreText);
        tvSectionTimeText.setText(sectionTimeText);

        showPlayView();
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {
        timeTaken = (int) clockView.getElapsedTime();
        clockView.pauseAnimationOnly();

        btnSubmit.setEnabled(false);
        answerBox.lockInputField(true);

        gameHasEnded = true;
    }

    private void onTimeExpired() {
        clockView.pause();
        answerBox.checkAnswer(question);
        gameHasEnded = true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        question = getArguments().getParcelable(ARGS_QUESTION_ENTITY);

        if (!gameHasEnded)
            answerBox.focusOnEditText();

        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!gameHasEnded)
            clockView.resume();
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

        int gameId = ((GameActivity) getActivity()).getCurrentGame().getGameId();

        tvCardScore.setText(GameProgressManager.getInstance().getSectionScoreText(gameId));
        tvCardTime.setText(GameProgressManager.getInstance().getSectionTimeText(gameId));

        btnSubmit.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        logo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError() {
        tvResultInstruction.setVisibility(View.VISIBLE);
        tvResultInstruction.setText(getString(R.string.game_a_error));

        GameProgressManager.getInstance().increaseSectionTime(getActivity(), MAX_TIME);

        showScoreView();
    }

    @Override
    public void onCorrect(int score) {
        tvResultInstruction.setVisibility(View.VISIBLE);
        tvResultInstruction.setText(getString(R.string.game_a_correct));

        // update the section score/time
        GameProgressManager.getInstance().increaseSectionScore(getActivity(), question.getScore());
        GameProgressManager.getInstance().increaseSectionTime(getActivity(), timeTaken);

        showScoreView();
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
        answerBox.setAnswerListener(null);
    }
}
