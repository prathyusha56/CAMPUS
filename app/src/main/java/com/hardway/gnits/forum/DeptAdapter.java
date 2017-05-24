package com.hardway.gnits.forum;

/**
 * Created by karth on 4/19/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hardway.gnits.R;

import java.util.List;

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.MyViewHolder> {

    private List<Dept_Details> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_dept);
          }
    }


    public DeptAdapter(List<Dept_Details> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dept, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Dept_Details movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
       
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}