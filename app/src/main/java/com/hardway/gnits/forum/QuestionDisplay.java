package com.hardway.gnits.forum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hardway.gnits.AnswerRegisterRequest;
import com.hardway.gnits.AppController;
import com.hardway.gnits.R;
import com.hardway.gnits.connection_check.ConnectivityReceiver;
import com.hardway.gnits.connection_check.MyApplication;
import com.hardway.gnits.login.LoginRegister;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class QuestionDisplay extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
TextView question,tag,question_head,timestamp;
ImageView button_Post;
SharedPreferences pref;

    ProgressBar progressBar_Question_display;
    RelativeLayout displayView_Scroll_bar;
    EditText answer_editText;

    private String URL_FEED = "http://highskidevelopers.com/CAMPUS/php/forum/Comments_Fetch.php";
    private static final String TAG = QuestionDisplay.class.getSimpleName();

    private List<Answers> answersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AnswersRegisterAdapter mAdapter;
    int id_last;

    int person_id_answered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_question_display);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("C People");



        question = (TextView) findViewById(R.id.question_text_question_display);
        tag = (TextView) findViewById(R.id.question_display_tag);
        question_head = (TextView) findViewById(R.id.question_head_question_display);
        timestamp = (TextView) findViewById(R.id.timestamp_question_display);

        answer_editText = (EditText) findViewById(R.id.answer_editText);
        button_Post = (ImageView) findViewById(R.id.btn_comment);

        progressBar_Question_display = (ProgressBar) findViewById(R.id.progressBar_Question_display);
        displayView_Scroll_bar = (RelativeLayout) findViewById(R.id.displayView_Scroll_bar);

        Intent intent = getIntent();
        final int Question_id = intent.getIntExtra("Question_id",1);
        final int person_id_asked = intent.getIntExtra("person_id_asked",1);
        String name = intent.getStringExtra("name");
        String tag_string = intent.getStringExtra("tag");
        String question_String = intent.getStringExtra("question");
        String question_head_string =intent.getStringExtra("question_head");
        String timeStamp_string = intent.getStringExtra("timeStamp");

        URL_FEED = "http://highskidevelopers.com/CAMPUS/php/forum/Comments_Fetch.php?question_id="+Question_id;

        Log.d("url",URL_FEED);

        recyclerView = (RecyclerView) findViewById(R.id.Question_display_recyclerView);

        mAdapter = new AnswersRegisterAdapter(answersList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Boolean get_connection = checkConnection();

        pref = getSharedPreferences("Downloaded_Values", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        person_id_answered =pref.getInt("id_DOWNLOAD",0);


        if(person_id_answered == 0)
        {

            getRegister();

        }else
        {
            if(get_connection) {
                getData();
            }
            else
            {
                getAlert();
            }

        }






        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Answers movie = answersList.get(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(timeStamp_string),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

        timestamp.setText(timeAgo);
        question.setText(question_String);
        tag.setText(tag_string);
        question_head.setText(question_head_string);

        button_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(person_id_answered == 0)
                {

                    getRegister();
                    return;
                }

                progressBar_Question_display.setVisibility(View.VISIBLE);
                displayView_Scroll_bar.setVisibility(View.GONE);

                pref = getSharedPreferences("Downloaded_Values", MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();

                long timestamp = System.currentTimeMillis();

                String answer = answer_editText.getText().toString().trim();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            {

                                progressBar_Question_display.setVisibility(View.GONE);
                                displayView_Scroll_bar.setVisibility(View.VISIBLE);
                                answer_editText.setText("");

//                              mAdapter.clearData();
                                URL_FEED = "http://highskidevelopers.com/CAMPUS/php/forum/Comments_Fetch_id.php?question_id="+Question_id+"&id="+id_last;


                                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                                        URL_FEED, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        VolleyLog.d(TAG, "Response: " + response.toString());
                                        if (response != null) {
                                            parseJsonFeedUpdate(response);
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

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                };
                AnswerRegisterRequest answerRegisterRequest = new AnswerRegisterRequest(person_id_answered,Question_id,answer,timestamp,responseListener);
                RequestQueue queue = Volley.newRequestQueue(QuestionDisplay.this);
                queue.add(answerRegisterRequest);

            }
        });

    }


    public void getRegister()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("You should register to use this content");
        alertDialogBuilder.setMessage("Do you want to register");

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent i = new Intent(QuestionDisplay.this, LoginRegister.class);
                startActivity(i);
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getRegister();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void getAlert()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuestionDisplay.this);

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


    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("noupdate");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                Answers item = new Answers();
                item.setAnswered_by_id(feedObj.getInt("answered_by"));

                item.setAnswer(feedObj.getString("answer"));
                item.setTimeStamp(feedObj.getString("timestamp"));
                item.setAnswer_id(feedObj.getInt("id"));
                item.setAnswerd_by(feedObj.getString("name"));

                Log.d("answered_by",feedObj.getString("name"));

                id_last = feedObj.getInt("id");

                answersList.add(item);

                Log.d("gello", feedObj.getString("answered_by"));

            }

            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void parseJsonFeedUpdate(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("update");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                Answers item = new Answers();
                item.setAnswered_by_id(feedObj.getInt("answered_by"));
                item.setAnswer(feedObj.getString("answer"));
                item.setTimeStamp(feedObj.getString("timestamp"));
                item.setAnswer_id(feedObj.getInt("id"));
                item.setAnswerd_by(feedObj.getString("name"));

                  id_last = feedObj.getInt("id");

                Log.d("last_id",id_last+"");

                answersList.add(item);

                Log.d("gello", feedObj.getString("answered_by"));
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
        private QuestionDisplay.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final QuestionDisplay.ClickListener clickListener) {
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

    @Override
    public void onPause() {
         super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(person_id_answered == 0)
        {

            getRegister();

        }

        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        return isConnected;
    }



    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

   public void getData()
    {
        JsonObjectRequest jsonReqbefore = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReqbefore);
    }
}
