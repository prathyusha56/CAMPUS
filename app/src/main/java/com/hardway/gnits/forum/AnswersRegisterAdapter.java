package com.hardway.gnits.forum;


import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardway.gnits.R;

import java.util.List;


/**
 * Created by DELL on 18-07-2016.
 */
public class AnswersRegisterAdapter extends RecyclerView.Adapter<AnswersRegisterAdapter.MyViewHolder> {

    private List<Answers> answersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answer_answer, answer_answered_by, timstamp_answers_display;

        public MyViewHolder(View view) {
            super(view);
            answer_answer = (TextView) view.findViewById(R.id.answer_answer);
            answer_answered_by = (TextView) view.findViewById(R.id.answer_answered_by);
            timstamp_answers_display = (TextView) view.findViewById(R.id.timstamp_answers_display);
        }
    }

    public AnswersRegisterAdapter(List<Answers> answersList) {
        this.answersList = answersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answers_display, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Answers answers = answersList.get(position);
        holder.answer_answer.setText(answers.getAnswer());
        holder.answer_answered_by.setText(answers.getAnswerd_by());

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(answers.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);


        holder.timstamp_answers_display.setText(timeAgo);
    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public void clearData() {
        int size = this.answersList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.answersList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

}
