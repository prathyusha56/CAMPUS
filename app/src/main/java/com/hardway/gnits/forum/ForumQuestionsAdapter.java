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
 * Created by DELL on 14-07-2016.
 */
public class ForumQuestionsAdapter extends RecyclerView.Adapter<ForumQuestionsAdapter.MyViewHolder>{

    private List<ForumList> questionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question,question_head, tag, name, timestamp;

        public MyViewHolder(View view) {
            super(view);
            question_head = (TextView) view.findViewById(R.id.question_head_list_forum);
            question = (TextView) view.findViewById(R.id.question_list_forum);
            tag = (TextView) view.findViewById(R.id.tag_forum);
            name = (TextView) view.findViewById(R.id.name_forum);
            timestamp = (TextView) view.findViewById(R.id.timestamp_forum);
        }
    }
    public ForumQuestionsAdapter(List<ForumList> questionsList) {
        this.questionsList = questionsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forum_questions, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ForumList forumList = questionsList.get(position);

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(forumList.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);


          holder.question_head.setText(forumList.getQuestion_head());
          holder.tag.setText(forumList.getTag());
          holder.name.setText(forumList.getName());
          holder.timestamp.setText(timeAgo);
          holder.question.setText(forumList.getQuestion());
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }


}
