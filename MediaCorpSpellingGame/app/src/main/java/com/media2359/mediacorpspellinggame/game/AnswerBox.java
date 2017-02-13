package com.media2359.mediacorpspellinggame.game;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Question;

/**
 * Created by xijunli on 13/2/17.
 */

public class AnswerBox extends RelativeLayout {

    private TextView tvId;

    private EditText etAnswer;

    private TextView tvResult;

    public AnswerBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AnswerBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_answer_box, this);

        tvId = (TextView) findViewById(R.id.tvId);
        etAnswer = (EditText) findViewById(R.id.etAnswer);
        tvResult = (TextView) findViewById(R.id.tvResult);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AnswerBox,
                0, 0);

        try {
            int id = a.getInt(R.styleable.AnswerBox_id, -1);
            setId(id);
        } finally {
            a.recycle();
        }

    }

    public void setId(int id) {
        if (id < 0){
            tvId.setVisibility(GONE);
        }else {
            tvId.setVisibility(VISIBLE);
            String display = id + ". ";
            tvId.setText(display);
        }
    }

    public void checkAnswer(Question question) {
        // lock the input
        etAnswer.setEnabled(false);
        // get the input
        String actualAnswer = etAnswer.getText().toString().trim();

        // if the answer is empty
        if (TextUtils.isEmpty(actualAnswer)){
            markAsWrong(question.getCorrectAnswer());
            return;
        }

        // check if the answer is correct
        if (question.getCorrectAnswer().equalsIgnoreCase(actualAnswer)){
            markAsCorrect(question.getScore());
        }else {
            markAsWrong(question.getCorrectAnswer());
        }
    }

    private void markAsCorrect(int score) {
        // change text color to green
        tvResult.setTextColor(getResources().getColor(R.color.green));
        // show green tick
        tvResult.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
        // show the awarded score
        String display = "+ " + score + " pts";
        tvResult.setText(display);
    }

    private void markAsWrong(String correctAnswer) {
        // change text color to red
        tvResult.setTextColor(getResources().getColor(R.color.red));
        // show red cross
        tvResult.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0);
        // show the correct answer
        tvResult.setText(correctAnswer);
    }
}
