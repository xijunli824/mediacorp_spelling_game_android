package com.media2359.mediacorpspellinggame.game.typeA;

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
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.game.typeB.GridQuestionsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 17/2/17.
 */

public class TypeAWaitingFragment extends Fragment {

    private static final String ARGS_QUESTION = "args_question";

    @BindView(R.id.btnGo)
    Button btnGo;

    @BindView(R.id.tvInstruction)
    TextView tvInstruction;

    @BindView(R.id.tvCurrentScore)
    TextView tvCurrentScore;

    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;

    private Question question;

    public static TypeAWaitingFragment newInstance(Question question) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_QUESTION, question);
        TypeAWaitingFragment fragment = new TypeAWaitingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = getArguments().getParcelable(ARGS_QUESTION);
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
        tvInstruction.setText(question.getInstruction());

        tvCurrentScore.setText(String.valueOf(GameProgressManager.getInstance().getTotalScore()));

        int qid = GameProgressManager.getInstance().getLastAttemptedQuestionPos() + 1;
        tvQuestionCount.setText("Question: " + qid + "/" + ((GameActivity) getActivity()).getCurrentGame().getQuestionCount());


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity)getActivity()).replaceFragment(SingleQuestionFragment.newInstance(question));
            }
        });
    }
}