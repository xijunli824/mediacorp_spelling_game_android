package com.media2359.mediacorpspellinggame.game.typeB;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.factory.GameRepo;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.game.typeA.SingleQuestionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 17/2/17.
 */

public class TypeBWaitingFragment extends Fragment {

    private static final String ARGS_GAME_INDEX = "game_index";

    @BindView(R.id.btnGo)
    Button btnGo;

    @BindView(R.id.tvInstruction)
    TextView tvInstruction;

    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;

    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;

    private int gameIndex;

    public static TypeBWaitingFragment newInstance(int gameIndex) {

        Bundle args = new Bundle();
        args.putInt(ARGS_GAME_INDEX, gameIndex);
        TypeBWaitingFragment fragment = new TypeBWaitingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameIndex = getArguments().getInt(ARGS_GAME_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game_waiting, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    private void bind() {

        Question question = GameRepo.getInstance().getQuestion(GameRepo.getInstance().getGame(gameIndex).getQuestionIdList()[0]);
        tvInstruction.setText(question.getInstruction());

        tvCurrentScore.setText(String.valueOf(GameProgressManager.getInstance().getTotalScore()));

        tvQuestionCount.setText("Question: 1/1");


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity)getActivity()).replaceFragment(GridQuestionsFragment.newInstance(gameIndex));
            }
        });
    }
}
