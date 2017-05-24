package com.hardway.gnits.dl;

/**
 * Created by karth on 1/9/2017.
 */

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hardway.gnits.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, artist_lr,duration_lr;
        public ImageView list_image_lr;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_lr);
            artist_lr = (TextView) view.findViewById(R.id.artist_lr);
            duration_lr = (TextView) view.findViewById(R.id.duration_lr);
            list_image_lr = (ImageView) view.findViewById(R.id.list_image_lr);

        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.artist_lr.setText(album.getDescr());
        holder.duration_lr.setText(album.getAuthor());
        // loading album cover using Glide library
         Glide.with(mContext).load(album.getThumbnail()).into(holder.list_image_lr);



    }

    /**
     * Showing popup menu when tapping on 3 dots
     */


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}