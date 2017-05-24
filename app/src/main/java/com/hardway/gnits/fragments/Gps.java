package com.hardway.gnits.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hardway.gnits.BrowserActivity;
import com.hardway.gnits.MapsActivity;
import com.hardway.gnits.R;
import com.hardway.gnits.chat.LoginActivity;
import com.hardway.gnits.chat.classes.ui.activities.SplashActivity;
import com.hardway.gnits.details.Notifications;
import com.hardway.gnits.forum.Dept;
import com.hardway.gnits.others.AlbumsAdapter;
import com.hardway.gnits.others.Details;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Gps extends Fragment {

    Button check,routes;

    public Gps() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Details> albumList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_gps, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_get);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new Gps.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Details details = albumList.get(position);

                int co = details.getID();

switch (co)
{
    case 1:
        Intent intent = new Intent(getActivity(), HomeFragment.class);
        startActivity(intent);
        break;
    case 2:
        Intent i = new Intent(getActivity(), Notifications.class);
        startActivity(i);
        break;
    case 3:
        Intent aa = new Intent(getActivity(), SplashActivity.class);
        startActivity(aa);
        break;
    case 4:
        Intent aa1 = new Intent(getActivity(), MapsActivity.class);
        startActivity(aa1);
        break;
    case 5:
        Intent aa2 = new Intent(getActivity(), Dept.class);
        startActivity(aa2);
        break;


}

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        checkAndRequestPermissions();
        prepareDetailss();

    return rootView;
    }



    private  void checkAndRequestPermissions() {
        String [] permissions=new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(),permission )!= PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    private void prepareDetailss() {

        Details a = new Details(1,"Project Post");
        albumList.add(a);

        a = new Details(2,"Notifications");
        albumList.add(a);

        a = new Details(3,"Chat");
        albumList.add(a);

        a = new Details(4,"Locate");
        albumList.add(a);

        a = new Details(5,"Forum Dept");
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


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Gps.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Gps.ClickListener clickListener) {
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



}
