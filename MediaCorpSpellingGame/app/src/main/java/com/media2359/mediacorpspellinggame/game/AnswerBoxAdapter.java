package com.media2359.mediacorpspellinggame.game;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;
import com.media2359.mediacorpspellinggame.widget.AnswerBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xijunli on 14/2/17.
 */

public class AnswerBoxAdapter extends RecyclerView.Adapter<AnswerBoxAdapter.AnswerBoxViewHolder> implements AnswerBox.AnswerListener{

    private List<Question> questionList;

    private boolean isEditMode = false;

    private boolean isChecking = false;

    private int totalScore = 0;

    private int correctAnswers = 0;

    private int totalQuestions = 0;

    private ResultListener listener;


    public AnswerBoxAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    public void setList(List<Question> listItems) {
        this.questionList = CommonUtils.checkNotNull(listItems);
    }

    public void refreshData(List<Question> newList) {
        setList(newList);
        notifyDataSetChanged();
    }

    @Override
    public AnswerBoxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer_box, parent, false);
        return new AnswerBoxViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnswerBoxViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.bind(question, position);

        holder.setAnswerListener(this);
        holder.enableEditMode(isEditMode);

        if (isChecking)
            holder.checkAnswer(question);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void enableEditMode(boolean enable) {
        isEditMode = enable;
        reset();
        notifyDataSetChanged();
    }

    @Override
    public synchronized void onError(int time) {
        totalQuestions += 1;

        if (!isEditMode)
            reportToListener();
    }

    @Override
    public synchronized void onCorrect(int score) {
        totalScore += score;
        correctAnswers += 1;
        totalQuestions += 1;

        if (!isEditMode)
            reportToListener();
    }

    private void reset() {
        totalQuestions = 0;
        totalScore = 0;
        correctAnswers = 0;
    }

    private synchronized void reportToListener() {
        if (totalQuestions >= getItemCount()){
            if (listener != null){
                listener.onResultSubmit(correctAnswers, totalQuestions, totalScore);
            }
        }
    }

    public void checkAnswers() {
        // reset
        reset();
        // there is no turning back
        isChecking = true;
        notifyDataSetChanged();
    }

    public void setResultListener(ResultListener listener) {
        this.listener = listener;
    }

    public interface ResultListener {

        void onResultSubmit(int correctAnswers, int totalQuestions, int totalScore);

    }

    static class AnswerBoxViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.answerBox)
        AnswerBox answerBox;

        AnswerBoxViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Question question, int adapterPos) {
            answerBox.setQuestion(question);
            answerBox.setId(adapterPos);
        }

        void setAnswerListener (AnswerBox.AnswerListener answerListener) {
            answerBox.setAnswerListener(answerListener);
        }

        void enableEditMode(boolean enable) {
            answerBox.enableEditMode(enable);
        }

        void checkAnswer(Question question) {
            answerBox.checkAnswer(question);
        }

    }
}
