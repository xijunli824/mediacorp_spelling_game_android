package com.media2359.mediacorpspellinggame.widget;

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

public class SecondsClockView extends LinearLayout implements Runnable{

    private View mClock = this;

    private boolean mPause = true;

    private boolean pauseViewOnly = false;

    private TabDigit mCharHighSecond;

    private TabDigit mCharLowSecond;

    private static final char[] SEXAGISIMAL = new char[]{'0', '1', '2', '3', '4', '5'};

    private long elapsedTime = 0;

    private TimeListener timeListener;

    public SecondsClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondsClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_clock_seconds, this);
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCharHighSecond = (TabDigit) findViewById(R.id.charHighSecond);
        mCharLowSecond = (TabDigit) findViewById(R.id.charLowSecond);

        mCharHighSecond.setChars(SEXAGISIMAL);
    }


    public void pause() {
        mPause = true;

        //mCharHighSecond.sync();
        //mCharLowSecond.sync();
    }

    public void pauseViewOnly() {
        pauseViewOnly = true;
    }

    public void resume() {
        mPause = false;
        pauseViewOnly = false;

        /* seconds*/
        int seconds = (int) elapsedTime;
        int highSecond = (seconds % 60) / 10;
        mCharHighSecond.setChar(highSecond);

        int lowSecond = (seconds%60 - highSecond * 10);
        mCharLowSecond.setChar(lowSecond);

        ViewCompat.postOnAnimationDelayed(mClock, this, 100);
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }

    public void showAlertClock() {
        if (pauseViewOnly)
            return;

        mCharHighSecond.setBackgroundColor(getResources().getColor(R.color.red));

        int seconds = (int) elapsedTime;
        int highSecond = (seconds % 60) / 10;
        mCharHighSecond.setChar(highSecond);

        mCharLowSecond.setBackgroundColor(getResources().getColor(R.color.red));
    }

    @Override
    public void run() {

        if(mPause){
            return;
        }

        elapsedTime += 1;

        if (!pauseViewOnly){

            mCharLowSecond.start();

            if (elapsedTime % 10 == 0) {
                mCharHighSecond.start();
            }
        }

        if (timeListener != null){
            timeListener.onSecond(elapsedTime);
        }

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public interface TimeListener {
        void onSecond(long seconds);
    }
}
