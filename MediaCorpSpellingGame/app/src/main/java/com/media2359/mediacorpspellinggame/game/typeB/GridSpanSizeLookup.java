package com.media2359.mediacorpspellinggame.game.typeB;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by xijunli on 22/2/17.
 */

public class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    @Override
    public int getSpanSize(int position) {
        if (position >= 0 && position < 8)
            return 1;
        else
            return 2;
    }

}

