package com.hardway.gnits.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hardway.gnits.AppController;
import com.hardway.gnits.R;
import com.hardway.gnits.dn.Parts;
import com.hardway.gnits.dn.PartsAdapter;
import com.hardway.gnits.pdf.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DN extends Fragment {

    private RecyclerView recyclerView;
    private PartsAdapter adapter;
    private List<Parts> albumList;

    private static final String TAG = DN.class.getSimpleName();

    private String URL_FEED = "http://www.highskidevelopers.com/CAMPUS/php/fest.php";


    public DN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dn, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_dn);

        albumList = new ArrayList<>();
        adapter = new PartsAdapter(getActivity(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


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
            JSONArray feedArray = response.getJSONArray("fest");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                Parts item = new Parts();
                item.setId(feedObj.getInt("id"));
                item.setHeading(feedObj.getString("name"));
                item.setAbout(feedObj.getString("description"));
                item.setRequest(feedObj.getString("link"));
                item.setThumbnail(feedObj.getString("image"));

                albumList.add(item);
            }

            // notify data changes to list adapater
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
