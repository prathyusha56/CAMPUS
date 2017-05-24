package com.hardway.gnits.dn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hardway.gnits.BrowserActivity;
import com.hardway.gnits.R;

import java.util.List;


/**
 * Created by karthik on 07-08-2016.
 */
public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.MyViewHolder> {

    private Context mContext;
    Intent i;
    private List<Parts> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description,request;
        public Button request_button,know_more;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.Text_heading);
            description = (TextView) view.findViewById(R.id.text_description);
            request = (TextView) view.findViewById(R.id.request_parts);
            request_button = (Button) view.findViewById(R.id.request_button);



        }
    }

    public PartsAdapter(Context mContext, List<Parts> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_made, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Parts album = albumList.get(position);
        holder.title.setText(album.getHeading());
        holder.description.setText(album.getAbout());

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        float dpWidth = pxWidth / displayMetrics.density;
        int h = (int) dpWidth;
        int pxHeight = displayMetrics.heightPixels;
        float dpHeight = pxHeight / displayMetrics.density;

        Glide.with(mContext).load(album.getThumbnail()).asBitmap().into(new SimpleTarget<Bitmap>(h, 200) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.title.setBackground(drawable);
                }
            }
        });

        holder.request.setText(album.getRequest());

        final int count = album.getId();



        holder.request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(album.getRequest()));
                mContext.startActivity(browserIntent);




            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
