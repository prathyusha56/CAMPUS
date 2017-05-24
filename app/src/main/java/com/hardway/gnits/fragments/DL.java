package com.hardway.gnits.fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hardway.gnits.R;
import com.hardway.gnits.dl.Album;
import com.hardway.gnits.dl.AlbumsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DL extends Fragment {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;


    public DL() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dl, container, false);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.linear);
// Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = height/2;
        params.width = width;
        layout.setLayoutParams(params);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_dl);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();



   return v;
    }


    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Album a = new Album("Signals and Systems", covers[0],"A.Anand Kumar","some description");
        albumList.add(a);

        a = new Album("Network Analysis 3 Edition", covers[1],"M. E. Van Valkenburg","some description");
        albumList.add(a);

        a = new Album("Engineering Circuit Analysis", covers[2],"William H. Hayt","some description");
        albumList.add(a);

        a = new Album("Electronic Devices and Circuits ", covers[3],"David A. Bell","some description");
        albumList.add(a);

        a = new Album("Electronic Devices and Circuits", covers[4],"J. B. Gupta","some description");
        albumList.add(a);

        a = new Album("Modern Digital Electronics", covers[5],"R P Jain","some description");
        albumList.add(a);

        a = new Album("Signals and Systems", covers[6],"S. Hamid Nawab","some description");
        albumList.add(a);

        a = new Album("Control Systems Engineering", covers[7],"Madan Gopal","some description");
        albumList.add(a);



        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


