package com.media2359.mediacorpspellinggame.game;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Section;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 13/2/17.
 */

public class SectionIntroFragment extends Fragment {

    private static final String ARGS_GAME_TYPE = "game_type";

    @BindView(R.id.ivGameIcon)
    ImageView ivGameIcon;

    @BindView(R.id.tvGameType)
    TextView tvGameType;

    @BindView(R.id.tvGameHint)
    TextView tvGameHint;

    @BindView(R.id.btnStart)
    Button btnStart;

    private Section game;

    public static SectionIntroFragment newInstance(Section game) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_GAME_TYPE, game);
        SectionIntroFragment fragment = new SectionIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = getArguments().getParcelable(ARGS_GAME_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_introduction, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).showNextQuestion();
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    private void bind() {
        String type = "அங்கம் " + game.getType();

        tvGameType.setText(type);
        tvGameHint.setText(game.getGameInstruction());

    }
}
