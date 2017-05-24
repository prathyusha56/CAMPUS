package com.hardway.gnits;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String URL_FEED = "https://testapplications.000webhostapp.com/gmtaedited/get.php";
    private String URL_FEED_1 = "https://testapplications.000webhostapp.com/gmtaedited/get_1.php";

    private static final String TAG = MapsActivity.class.getSimpleName();
    float lat,lon;
    Handler handler = new Handler();
    Runnable runnableCode;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        a=0;
        // startService(new Intent(getBaseContext(), MyService.class));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng ab = new LatLng(14.447886, 78.237273);

        // Add a marker in Sydney and move the camera

        runnableCode = new Runnable() {
            @Override
            public void run() {
                mMap.clear();

                MarkerOptions marker = new MarkerOptions().position(new LatLng(14.447886, 78.237273));

// Changing marker icon
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

// adding marker
                mMap.addMarker(marker);

                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(ab);
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(19);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);


                mMap.addCircle(new CircleOptions()
                        .center(new LatLng(14.447886, 78.237273))
                        .radius(45)
                        .strokeColor(getResources().getColor(R.color.colorAccent)).strokeWidth(2)
                        .fillColor(getResources().getColor(R.color.blue_100)));

                // Do something here on the main thread
                Log.e("Handlers", "Called on main thread");

                a++;
                if(a%2==0){
                    refresh();
                }
                else{
                    refresh_1();
                }

                handler.postDelayed(runnableCode, 60000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);




    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);


                String a =feedObj.getString("lat");
                String b = feedObj.getString("lon");
                String name = feedObj.getString("name");

                lat = Float.parseFloat(a);
                lon = Float.parseFloat(b);


                LatLng sydney = new LatLng(lat, lon);

                Double dist = distance(14.447886, 78.237273,lat,lon,"K")*1000;

                Log.e("dist",dist+"");

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon)).title(name);

                if(dist<=45) {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else if(dist>45 && dist<=500)
                {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }else if(dist>500)
                {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
// adding marker
                mMap.addMarker(marker);



            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJsonFeed_1(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed_1");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);


                String a =feedObj.getString("lat");
                String b = feedObj.getString("lon");
                String name = feedObj.getString("name");

                lat = Float.parseFloat(a);
                lon = Float.parseFloat(b);

                Double dist = distance(14.447886, 78.237273,lat,lon,"K")*1000;

                Log.e("dist",dist+"");




                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon)).title(name);

                if(dist<=45) {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else if(dist>45 && dist<=500)
                {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }else if(dist>500)
                {
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
// adding marker
                mMap.addMarker(marker);

                // url might be null sometimes

            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refresh(){

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


    }



    public void refresh_1(){

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED_1, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed_1(response);
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


    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}
