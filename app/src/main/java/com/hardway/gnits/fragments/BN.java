package com.hardway.gnits.fragments;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardway.gnits.MainActivity;
import com.hardway.gnits.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BN extends Fragment {


    public BN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_bn, container, false);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Alert for you");
        mBuilder.setContentText("Bus will be late by 30 minutes");

        Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());


    return v;
    }

}
