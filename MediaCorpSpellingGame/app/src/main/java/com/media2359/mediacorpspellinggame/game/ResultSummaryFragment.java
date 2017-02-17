package com.media2359.mediacorpspellinggame.game;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media2359.mediacorpspellinggame.R;

import butterknife.ButterKnife;

/**
 * Created by xijunli on 17/2/17.
 */

public class ResultSummaryFragment extends Fragment {

    public static ResultSummaryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ResultSummaryFragment fragment = new ResultSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result_summary, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
    }
}
