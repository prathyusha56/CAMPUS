package com.hardway.gnits.forum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hardway.gnits.R;

import java.util.ArrayList;
import java.util.List;

public class Dept extends AppCompatActivity {
    private List<Dept_Details> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeptAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_dept);

        mAdapter = new DeptAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new Dept.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Dept_Details movie = movieList.get(position);

                int id = movie.getId();

                Intent i = new Intent(Dept.this,Tags.class);
                i.putExtra("id",id);
                startActivity(i);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        Dept_Details movie = new Dept_Details("CIVIL",1);
        movieList.add(movie);

        movie = new Dept_Details("EEE",2);
        movieList.add(movie);
        movie = new Dept_Details("MECH",3);
        movieList.add(movie);
        movie = new Dept_Details("ECE",4);
        movieList.add(movie);
        movie = new Dept_Details("CSE",5);
        movieList.add(movie);
        movie = new Dept_Details("BIO",6);
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Dept.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Dept.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}
