package com.hardway.gnits.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hardway.gnits.AppController;
import com.hardway.gnits.R;
import com.hardway.gnits.connection_check.ConnectivityReceiver;
import com.hardway.gnits.connection_check.MyApplication;
import com.hardway.gnits.forum.DividerItemDecoration;
import com.hardway.gnits.forum.ForumList;
import com.hardway.gnits.forum.ForumQuestionsAdapter;
import com.hardway.gnits.forum.QuestionDisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class Questions extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{

    private String URL_FEED = "http://highskidevelopers.com/CAMPUS/php/forum/AllQuestionsRetrival.php";
    private static final String TAG = Questions.class.getSimpleName();
    private List<ForumList> forumList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ForumQuestionsAdapter mAdapter;
    SharedPreferences pref;


    public Questions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_questions, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_questions);

        mAdapter = new ForumQuestionsAdapter(forumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        pref = getActivity().getSharedPreferences("Downloaded_Values", getActivity().MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        int person_id = pref.getInt("id_DOWNLOAD", 1);

        URL_FEED = "http://www.highskidevelopers.com/cflash/QuestionsRetrival.php?person_id="+person_id;



        if(checkConnection()) {
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);

        }
        else {

            getAlert();

        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ForumList forum = forumList.get(position);

                int Question_id = forum.getQuestion_id();
                int person_id_asked = forum.getPerson_id();
                String name = forum.getName();
                String tag = forum.getTag();
                String question = forum.getQuestion();
                String question_head = forum.getQuestion_head();
                String timeStamp = forum.getTimeStamp();

                Intent i = new Intent(getActivity(), QuestionDisplay.class);
                i.putExtra("Question_id",Question_id);
                i.putExtra("person_id_asked",person_id_asked);
                i.putExtra("name",name);
                i.putExtra("tag",tag);
                i.putExtra("question",question);
                i.putExtra("question_head",question_head);
                i.putExtra("timeStamp",timeStamp);
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




        return rootView;
    }



    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                ForumList item = new ForumList();
                item.setQuestion_id(feedObj.getInt("question_id"));
                item.setPerson_id(feedObj.getInt("person_id"));
                item.setQuestion_head(feedObj.getString("question_head"));
                item.setQuestion(feedObj.getString("question"));
                item.setTag(feedObj.getString("tag"));
                item.setTimeStamp(feedObj.getString("timestamp"));
                item.setCount(feedObj.getInt("count"));
                item.setName(feedObj.getString("name"));

                forumList.add(item);

                Log.d("gello", feedObj.getString("question_id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Questions.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Questions.ClickListener clickListener) {
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

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        return isConnected;
    }


    @Override
    public void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }


    public void getAlert()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("NO INTERNET CONNECTION");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to switch on the Internet?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_import_export_black_48dp);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                getAlert();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

        // alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);

    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {


        super.onDestroy();
    }

}

