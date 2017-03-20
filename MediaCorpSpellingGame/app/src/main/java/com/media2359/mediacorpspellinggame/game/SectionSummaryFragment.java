package com.media2359.mediacorpspellinggame.game;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Section;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.factory.GameRepo;
import com.media2359.mediacorpspellinggame.widget.ScoreText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 21/2/17.
 */

public class SectionSummaryFragment extends Fragment {

    private static final String ARGS_GAME_INDEX = "args_game_index";

    @BindView(R.id.tvCongratulations)
    TextView tvCongratulations;

    @BindView(R.id.stGameA)
    ScoreText stGameA;

    @BindView(R.id.stGameB)
    ScoreText stGameB;

    @BindView(R.id.stGameC)
    ScoreText stGameC;

    @BindView(R.id.stGameD)
    ScoreText stGameD;

    @BindView(R.id.stGameE)
    ScoreText stGameE;

    @BindView(R.id.stTotalScore)
    ScoreText stTotalScore;

    @BindView(R.id.stTotalTime)
    ScoreText stTotalTime;

    @BindView(R.id.btnNext)
    Button btnNext;

    private int gameIndex = -1;

    public static SectionSummaryFragment newInstance(int gameIndex) {

        Bundle args = new Bundle();
        args.putInt(ARGS_GAME_INDEX, gameIndex);
        SectionSummaryFragment fragment = new SectionSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game_summary, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gameIndex = getArguments().getInt(ARGS_GAME_INDEX);

        bind();
    }

    private void bind() {
        Section game = GameRepo.getInstance().getSection(gameIndex);

        String congratulations = "வாழ்த்துகள்!\n அங்கம் "+ game.getType()+" முடிவடைந்தது!";

        tvCongratulations.setText(congratulations);

        // game index = 0 >> first game GAME A
        stGameA.setRightText(GameProgressManager.getInstance().getSectionScoreText(0));

        if (gameIndex >= 1){
            stGameB.setVisibility(View.VISIBLE);
            stGameB.setRightText(GameProgressManager.getInstance().getSectionScoreText(1));
        }

        if (gameIndex >= 2){
            stGameC.setVisibility(View.VISIBLE);
            stGameC.setRightText(GameProgressManager.getInstance().getSectionScoreText(2));
        }

        if (gameIndex >= 3){
            stGameD.setVisibility(View.VISIBLE);
            stGameD.setRightText(GameProgressManager.getInstance().getSectionScoreText(3));
        }

        if (gameIndex >= 4) {
            stGameE.setVisibility(View.VISIBLE);
            stGameE.setRightText(GameProgressManager.getInstance().getSectionScoreText(4));
        }

        stTotalScore.setRightText(GameProgressManager.getInstance().getTotalScoreString());
        stTotalTime.setRightText(GameProgressManager.getInstance().getTimeTakenString());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).startNextGame();
            }
        });

    }
}
