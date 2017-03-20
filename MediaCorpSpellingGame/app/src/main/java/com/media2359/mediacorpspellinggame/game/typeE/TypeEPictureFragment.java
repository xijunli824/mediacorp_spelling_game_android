package com.media2359.mediacorpspellinggame.game.typeE;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.game.GameActivity;
import com.media2359.mediacorpspellinggame.game.typeA.TypeAWaitingFragment;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xijunli on 20/3/17.
 */

public class TypeEPictureFragment extends Fragment {

    public static TypeEPictureFragment newInstance() {

        Bundle args = new Bundle();

        TypeEPictureFragment fragment = new TypeEPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_picture, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.ivFullScreenImage)
    public void onFullScreenImageClick() {
        CommonUtils.makeHoldOnAlertDialog(getActivity(), getString(R.string.game_e_image_message), getString(R.string.game_e_image_title), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((GameActivity) getActivity()).replaceFragment(TypeAWaitingFragment.newInstance(null, "5"));
            }
        }).show();
    }
}
