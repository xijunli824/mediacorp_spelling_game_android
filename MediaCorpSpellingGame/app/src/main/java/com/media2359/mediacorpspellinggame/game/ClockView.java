package com.media2359.mediacorpspellinggame.game;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.media2359.mediacorpspellinggame.R;
import com.xenione.digit.TabDigit;

/**
 * Created by xijunli on 13/2/17.
 */

public class ClockView extends LinearLayout implements Runnable{

    private View mClock = this;

    private boolean mPause = true;

    private TabDigit mCharHighSecond;

    private TabDigit mCharLowSecond;

    private long elapsedTime = 0;

    private TimeListener timeListener;

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_clock, this);
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCharHighSecond = (TabDigit) findViewById(R.id.charHighSecond);
        mCharLowSecond = (TabDigit) findViewById(R.id.charLowSecond);
    }


    public void pause() {
        mPause = true;

        //mCharHighSecond.sync();
        //mCharLowSecond.sync();
    }

    public void resume() {
        mPause = false;

        /* seconds*/
        int seconds = (int) elapsedTime % 10;
        int highSecond = seconds / 10;
        mCharHighSecond.setChar(highSecond);

        int lowSecond = (seconds - highSecond * 10);
        mCharLowSecond.setChar(lowSecond);

        elapsedTime = lowSecond + highSecond * 10;

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }

    @Override
    public void run() {
        if(mPause){
            return;
        }

        elapsedTime += 1;

        mCharLowSecond.start();

        if (elapsedTime % 10 == 0) {
            mCharHighSecond.start();
        }

        if (timeListener != null){
            timeListener.onSecond(elapsedTime);
        }

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }

    public interface TimeListener {
        void onSecond(long seconds);
    }
}
