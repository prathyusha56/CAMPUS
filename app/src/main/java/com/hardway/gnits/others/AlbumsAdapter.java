package com.hardway.gnits.others;

/**
 * Created by karth on 2/11/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardway.gnits.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Details> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_an);
        }
    }


    public AlbumsAdapter(Context mContext, List<Details> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card_ab, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Details album = albumList.get(position);
        holder.title.setText(album.getTitle());

        switch (album.getID()%5)
        {
            case 1:
                holder.title.setBackgroundResource(R.color.grey_200);
                break;
            case 2:
                holder.title.setBackgroundResource(R.color.grey_300);
                break;

            case 3:
                holder.title.setBackgroundResource(R.color.grey_400);
                break;

            case 4:
                holder.title.setBackgroundResource(R.color. grey_500);
                break;

            case 0:
                holder.title.setBackgroundResource(R.color.grey_600);
                break;


        }


    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }
}