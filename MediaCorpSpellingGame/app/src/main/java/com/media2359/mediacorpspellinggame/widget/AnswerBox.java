package com.media2359.mediacorpspellinggame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Question;

import java.util.List;

/**
 * Created by xijunli on 13/2/17.
 */

public class AnswerBox extends RelativeLayout {

    private TextView tvId;

    private EditText etAnswer;

    private TextView tvResult;

    private boolean isCorrect = false;

    private Question question;

    private AnswerListener answerListener;

    private boolean overrideAnswer = false;

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
            setLeftId(id);
        } finally {
            a.recycle();
        }

    }

    public void setLeftId(int id) {
        if (id < 0){
            tvId.setVisibility(GONE);
        }else {
            tvId.setVisibility(VISIBLE);
            String display = id + ". ";
            tvId.setText(display);
        }
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        etAnswer.setText(answer);
    }

    public void forceChangeResult(boolean isCorrect) {

        this.isCorrect = isCorrect;

        lockInputField(true);

        if (isCorrect){
            markAsCorrect(question.getScore());
        } else {
            markAsWrong(question.getCorrectAnswers().get(0));
        }
    }

    public void checkAnswer(Question question) {
        // lock the input
        lockInputField(true);

        if (overrideAnswer && answerListener != null){

            if (isCorrect)
                answerListener.onCorrect(question.getScore());
            else
                answerListener.onError();

            return;
        }

        this.question = question;
        // get the input
        String actualAnswer = etAnswer.getText().toString().trim();
        List<String> correctAnswers = question.getCorrectAnswers();

        // if the answer is empty
        if (TextUtils.isEmpty(actualAnswer)){
            markAsWrong(correctAnswers.get(0));
            return;
        }

        // check if the answer is correct
        if (correctAnswers.contains(actualAnswer)){
            markAsCorrect(question.getScore());
        }else {
            markAsWrong(correctAnswers.get(0));
        }
    }

    private void markAsCorrect(int score) {
        isCorrect = true;
        // change text color to green
        tvResult.setTextColor(getResources().getColor(R.color.green));
        // show green tick
        //tvResult.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
        etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tick, 0);
        // show the awarded score
        String display = "+ " + score + " pts";
        tvResult.setText(display);

        if (answerListener != null)
            answerListener.onCorrect(score);
    }

    private void markAsWrong(String correctAnswer) {
        isCorrect = false;
        // change text color to red
        tvResult.setTextColor(getResources().getColor(R.color.red));
        // show red cross
        //tvResult.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0);
        etAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0);
        // show the correct answer
        tvResult.setText(correctAnswer);

        if (answerListener != null)
            answerListener.onError();
    }

    public void enableEditMode(boolean enable) {

        if (enable){
            overrideAnswer = true;
            tvResult.setOnClickListener(onClickToggleResult);
            tvResult.setClickable(true);
        }else{
            tvResult.setOnClickListener(null);
            tvResult.setClickable(false);
        }
    }

    private OnClickListener onClickToggleResult = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isCorrect)
                markAsCorrect(question.getScore());
            else
                markAsWrong(question.getCorrectAnswers().get(0));
        }
    };

    public void lockInputField(boolean lock) {
        etAnswer.setEnabled(!lock);
    }

    public void changeToActionNext(boolean next){
        if (next)
            etAnswer.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        else
            etAnswer.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    public void setAnswerListener(AnswerListener answerListener) {
        this.answerListener = answerListener;
    }

    public void addTextWatcher(TextWatcher textWatcher) {
        etAnswer.addTextChangedListener(textWatcher);
    }

    public interface AnswerListener {

        void onError();

        void onCorrect(int score);

    }
}
