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
import com.media2359.mediacorpspellinggame.data.Game;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 13/2/17.
 */

public class GameIntroFragment extends Fragment {

    private static final String ARGS_GAME_TYPE = "game_type";

    @BindView(R.id.ivGameIcon)
    ImageView ivGameIcon;

    @BindView(R.id.tvGameType)
    TextView tvGameType;

    @BindView(R.id.tvGameHint)
    TextView tvGameHint;

    @BindView(R.id.btnStart)
    Button btnStart;

    private Game game;

    public static GameIntroFragment newInstance(Game game) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_GAME_TYPE, game);
        GameIntroFragment fragment = new GameIntroFragment();
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
                if (game.getType().equalsIgnoreCase("B")){
                    ((GameActivity) getActivity()).showNextMultipleQuestionFragment();
                }else {
                    ((GameActivity) getActivity()).showNextSingleQuestionFragment();
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    private void bind() {
        String type = "GAME " + game.getType();

//        if (type.equalsIgnoreCase("A")) {
//            tvGameType.setText("GAME A");
//            //ivGameIcon.setImageResource(R.drawable.ic_game_a);
//        } else if (type.equalsIgnoreCase("B")) {
//            tvGameType.setText("GAME B");
//            //ivGameIcon.setImageResource(R.drawable.ic_game_b);
//        } else if (type.equalsIgnoreCase("C")) {
//            tvGameType.setText("GAME C");
//            //ivGameIcon.setImageResource(R.drawable.ic_game_c);
//        } else if (type.equalsIgnoreCase("D")) {
//            tvGameType.setText("GAME D");
//            //ivGameIcon.setImageResource(R.drawable.ic_game_c);
//        } else if (type.equalsIgnoreCase("E")) {
//            tvGameType.setText("GAME E");
//            //ivGameIcon.setImageResource(R.drawable.ic_game_c);
//        }

        tvGameType.setText(type);
        tvGameHint.setText(game.getGameInstruction());

    }
}
