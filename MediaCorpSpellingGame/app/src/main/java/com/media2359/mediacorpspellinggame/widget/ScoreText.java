package com.media2359.mediacorpspellinggame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;

/**
 * Created by xijunli on 21/2/17.
 */

public class ScoreText extends LinearLayout {

    TextView tvLeft, tvRight;
    
    public ScoreText(Context context) {
        super(context);
    }

    public ScoreText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ScoreText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_score_text, this);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        tvLeft = (TextView) findViewById(R.id.tvCardLeft);
        tvRight = (TextView) findViewById(R.id.tvCardRight);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScoreText,
                0, 0);

        try {
            String left = a.getString(R.styleable.ScoreText_left_text);
            String right = a.getString(R.styleable.ScoreText_right_text);

            if (!TextUtils.isEmpty(left)){
                setLeftText(left);
            }

            if (!TextUtils.isEmpty(right)) {
                setRightText(right);
            }

        } finally {
            a.recycle();
        }
    }

    public void setLeftText(String left) {
        tvLeft.setText(left);
    }

    public void setRightText(String right) {
        tvRight.setText(right);
    }
}
