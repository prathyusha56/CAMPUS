package com.hardway.gnits.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hardway.gnits.AppController;
import com.hardway.gnits.MainActivity;
import com.hardway.gnits.R;
import com.hardway.gnits.pdf.FeedItem;
import com.hardway.gnits.pdf.FeedListPdf;
import com.hardway.gnits.upload.Upload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Share extends Fragment {
    private ListView listView;
    private FeedListPdf listAdapter;
    private List<com.hardway.gnits.pdf.FeedItem> FeedItem;
    String URL_FEED = "http://www.highskidevelopers.com/CAMPUS/php/pdf.php";
    private static final String TAG = Share.class.getSimpleName();
    public Share() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_share, container, false);


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), Upload.class);
                startActivity(intent);
            }
        });


        listView = (ListView) v.findViewById(R.id.lv);


        FeedItem = new ArrayList<FeedItem>();

        listAdapter = new FeedListPdf(getActivity(),FeedItem);
        listView.setAdapter(listAdapter);

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
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
        AppController.getInstance().addToRequestQueue(jsonReq);

        return v;
    }


    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("pdf");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
                item.setStatus(feedObj.getString("link"));

                Log.e("link",feedObj.getString("link"));

                FeedItem.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}