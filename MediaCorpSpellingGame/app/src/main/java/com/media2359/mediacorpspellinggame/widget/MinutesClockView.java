package com.media2359.mediacorpspellinggame.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.media2359.mediacorpspellinggame.R;
import com.xenione.digit.TabDigit;

/**
 * Created by xijunli on 14/2/17.
 */

public class MinutesClockView extends LinearLayout implements Runnable{

    private View mClock = this;

    private boolean mPause = true;

    private boolean pauseViewOnly = false;

    private TabDigit mCharHighSecond;

    private TabDigit mCharLowSecond;

    private TabDigit mCharLowMinute;

    private long elapsedTime = 0;

    private static final char[] SEXAGISIMAL = new char[]{'0', '1', '2', '3', '4', '5'};

    private TimeListener timeListener;

    public MinutesClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MinutesClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_clock_minutes, this);
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCharHighSecond = (TabDigit) findViewById(R.id.charHighSecond);
        mCharLowSecond = (TabDigit) findViewById(R.id.charLowSecond);
        mCharLowMinute = (TabDigit) findViewById(R.id.charLowMinute);

        mCharHighSecond.setChars(SEXAGISIMAL);
    }


    public void pause() {
        mPause = true;
    }

    public void pauseViewOnly() {
        pauseViewOnly = true;
    }

    public void pauseAndSync() {
        mPause = true;

        mCharHighSecond.sync();
        mCharLowSecond.sync();
        mCharLowMinute.sync();
    }

    public void showAlertClock() {

        if (pauseViewOnly)
            return;

        int seconds = (int) elapsedTime;

        int highSecond = (seconds % 60) / 10;
        
        mCharHighSecond.setBackgroundColor(getResources().getColor(R.color.red));
        mCharHighSecond.setChar(highSecond);

        mCharLowSecond.setBackgroundColor(getResources().getColor(R.color.red));

        int lowMinute = seconds / 60;

        mCharLowMinute.setBackgroundColor(getResources().getColor(R.color.red));
        mCharLowMinute.setChar(lowMinute);
    }

    public void resume() {
        mPause = false;
        //pauseViewOnly = false;

        /* seconds*/
//        int seconds = (int) elapsedTime;
//        int highSecond = (seconds % 60) / 10;
//        mCharHighSecond.setChar(highSecond);
//
//        int lowSecond = seconds % 10;
//        mCharLowSecond.setChar(lowSecond);
//
//        int minutes = (int) Math.floor(elapsedTime / 60);
//        mCharLowMinute.setChar(minutes);
//
//        mCharHighSecond.sync();
//        mCharLowSecond.sync();
//        mCharLowMinute.sync();

        //elapsedTime = lowSecond + highSecond * 10;

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

        if (! pauseViewOnly) {

//            if (elapsedTime % 10 == 0) {
//                mCharHighSecond.start();
//            }
//
//            if (elapsedTime % 60 == 0) {
//                mCharLowMinute.start();
//            }

            if (elapsedTime % 10 == 0) {
                int highSecond = (int) (elapsedTime % 60) / 10;
                mCharHighSecond.setChar(highSecond);
            }

            if (elapsedTime % 60 == 0) {
                int minutes = (int) Math.floor(elapsedTime / 60);
                mCharLowMinute.setChar(minutes);
            }

            int lowSecond = (int) elapsedTime % 10;
            mCharLowSecond.setChar(lowSecond);

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
