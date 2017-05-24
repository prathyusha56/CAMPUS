package com.hardway.gnits.pdf;

/**
 * Created by Darshu on 15-Mar-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hardway.gnits.R;

import java.util.List;

public class FeedListPdf extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private CardView cardView;



    public FeedListPdf(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;



    }



    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_list_pdf, null);


        cardView = (CardView) convertView.findViewById(R.id.card_view);

        TextView name = (TextView) convertView.findViewById(R.id.name);



        final FeedItem item = feedItems.get(position);

        name.setText(item.getName());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = item.getId();
               String s = item.getStatus();
                String name =item.getStatus();

                Intent i = new Intent(activity,PdfView.class);
                i.putExtra("name",name);
                activity.startActivity(i);

            }
        });


        return convertView;
    }

}