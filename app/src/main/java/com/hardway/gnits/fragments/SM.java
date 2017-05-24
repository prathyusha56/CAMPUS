package com.hardway.gnits.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hardway.gnits.AppController;
import com.hardway.gnits.R;
import com.hardway.gnits.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SM extends Fragment {

    int dept,course;
String year;
    private static final String TAG = SM.class.getSimpleName();
    private String URL_FEED = "http://highskidevelopers.com/CAMPUS/php/forum/tt.php?gop=";
    TextView s1,s2,s3,s4;
    public SM() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_sm, container, false);


        s1 = (TextView) v.findViewById(R.id.subject1);
        s2 = (TextView) v.findViewById(R.id.subject2);
        s3 = (TextView) v.findViewById(R.id.subject3);
        s4 = (TextView) v.findViewById(R.id.subject4);

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        SharedPreferences pref = getActivity().getSharedPreferences("Downloaded_Values", getActivity().MODE_PRIVATE);




        dept = pref.getInt("group_DOWNLOAD",1);
        year = pref.getString("year_DOWNLOAD","");
        course = pref.getInt("course_DOWNLOAD",1);

        URL_FEED = URL_FEED+dept+"&course="+course+"&year="+year+"&day="+dayOfWeek;

        Log.e("URL",URL_FEED);


        // making fresh volley request and getting json
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {

                        try {
                            JSONArray feedArray = response.getJSONArray("tt");

                            for (int i = 0; i < feedArray.length(); i++) {
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                String sub1 = feedObj.getString("p1");
                                String sub2 = feedObj.getString("p2");
                                String sub3 = feedObj.getString("p3");
                                String sub4 = feedObj.getString("p4");


                                s1.setText(sub1);
                                s2.setText(sub2);
                                s3.setText(sub3);
                                s4.setText(sub4);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



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






        return v;
    }

}
