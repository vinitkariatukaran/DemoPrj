package com.demoprj;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.demoprj.Constant.AppConstant;
import com.demoprj.ParseModel.User;
import com.demoprj.Utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap mMap;
    Double cLat;
    Double cLon;
    String userLatLng;
    Spinner spnRoutes;
    List<List<HashMap<String, String>>> routes = null;
    MarkerOptions markerlocation = new MarkerOptions();
    Marker marker;
    Button fabNearByPlaces;
    int CustomerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        spnRoutes = (Spinner) findViewById(R.id.spnRoutes);
        fabNearByPlaces = (Button) findViewById(R.id.fabNearByPlaces);

        spnRoutes.setOnItemSelectedListener(this);
        Bundle b = getIntent().getExtras();
        CustomerId = b.getInt("ObjectId");
        cLat = b.getDouble("cLat");
        cLon = b.getDouble("cLon");
        userLatLng = b.getString("userLatLng");
        fabNearByPlaces.setVisibility(View.GONE);
        fabNearByPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(MapActivity.this);
                progressDialog.setTitle("Fetcing Nearby User");
                progressDialog.setMessage("Please wait");
                progressDialog.setCanceledOnTouchOutside(false);
                if (Utils.isInternetAvailable(MapActivity.this)) {
                    progressDialog.show();
                    ParseQuery<User> userQuery = ParseQuery.getQuery(User.class);
                    userQuery.whereNotEqualTo(AppConstant.USER_CUSTOMER_ID, CustomerId);
                    userQuery.findInBackground(new FindCallback<User>() {
                        @Override
                        public void done(List<User> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    String[] userLatLng1 = userLatLng.split(",");

                                        for (int i = 0; i < objects.size(); i++) {
                                            if (objects.get(i).has(AppConstant.USER_LATLNG) && objects.get(i).getLatLng().length()>0){
                                            String[] endLat = objects.get(i).getLatLng().split(",");
                                            float[] result = new float[1];
                                            Location.distanceBetween(Double.parseDouble(userLatLng1[0]), Double.parseDouble(userLatLng1[1]), Double.parseDouble(endLat[0]), Double.parseDouble(endLat[1]), result);
                                            if (result[0] < 5000) {
                                                MarkerOptions marker2 = new MarkerOptions();
                                                marker2 = new MarkerOptions().position(new LatLng(Double.parseDouble(endLat[0]), Double.parseDouble(endLat[1]))).title(objects.get(i).getFirstName() + " " + objects.get(i).getLastName());
                                                marker2.draggable(true);
                                                marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                                                mMap.addMarker(marker2);
                                                // distance between first and second location is less than 5km
                                            }
                                        }
                                    if (i == (objects.size() - 1)) {
                                        progressDialog.dismiss();
                                    }
                                }
                            } else {
                                progressDialog.dismiss();
                            }
                            String[] userLat = userLatLng.split(",");
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(userLat[0]), Double.parseDouble(userLat[1])), 15.0f));
                        }

                        else

                        {
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            else

            {
                Toast.makeText(MapActivity.this, "Internet Connection is not avaliable. Please try again later", Toast.LENGTH_LONG).show();
            }

            ArrayList<String> aList = new ArrayList<String>();
            aList.add("23.014350,72.546643");
            aList.add("23.039229,72.547859");
            aList.add("23.006007,72.588813");
            aList.add("22.979345,72.531044");
            aList.add("23.018254,72.559586");
                /*String[] userLatLng1 = userLatLng.split(",");
                for(int i = 0;i<aList.size();i++) {
                    String[] endLat = aList.get(i).split(",");
                    float[] result = new float[1];
                    Location.distanceBetween(Double.parseDouble(userLatLng1[0]), Double.parseDouble(userLatLng1[1]), Double.parseDouble(endLat[0]), Double.parseDouble(endLat[1]), result);
                    if (result[0] < 5000) {
                        MarkerOptions marker2 = new MarkerOptions();
                        marker2 = new MarkerOptions().position(new LatLng(Double.parseDouble(endLat[0]), Double.parseDouble(endLat[1]))).title(" Customer "+i);
                        marker2.draggable(true);
                        marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                        mMap.addMarker(marker2);
                        // distance between first and second location is less than 5km
                    }
                    if(i==(aList.size()-1)){
                        progressDialog.dismiss();
                    }
                }*/
        }
    });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(cLat, cLon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setOnMyLocationChangeListener(this);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.setTitle(marker.getPosition()+"");
            }
        });
        if(marker!=null){
            marker.remove();
        }
        String[] latlng = userLatLng.split(",");
        markerlocation= new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]))).title("User Location");
        markerlocation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        marker = mMap.addMarker(markerlocation);//new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You are Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).visible(true));
        marker.showInfoWindow();
//        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + cLat+","+cLon + "&destination=" + userLatLng +"&alternatives=true&key=" + getResources().getString(R.string.google_maps_key);
//        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + cLat+","+cLon + "&destination=" + userLatLng +"&alternatives=true&key=" + getResources().getString(R.string.google_maps_key);
//        RequestQueue queue = CustomVolleyRequestQueue.getInstance(this).getRequestQueue();
//        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.GET,url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("Response",response.toString());
//                        DirectionsJSONParser parser = new DirectionsJSONParser();
//                        routes = parser.parse(response);
//                        AddRouteToSpinner();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error",error.getMessage());
//            }
//        });
//        queue.add(jsonObjReq1);
    }

    private void AddRouteToSpinner() {
        if(routes.size()>1) {
            spnRoutes.setVisibility(View.VISIBLE);
            String[] columns = new String[routes.size()];
            for(int i =0;i<routes.size();i++)
            {
                columns[i]  = "Route "+(i+1);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapActivity.this,android.R.layout.simple_dropdown_item_1line,columns);
            spnRoutes.setAdapter(adapter);
        }
        else{
            spnRoutes.setVisibility(View.GONE);
        }
//        getRoute(routes.get(0));
        Log.i("asdf", "asdf" + routes);
    }
    private void getRoute(List<HashMap<String, String>> routePath) {
        mMap.clear();

//        DirectionActivity.routes.addAll(result);
        long distance=0;
        long duration=0;
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        boolean flag = true;
        points = new ArrayList<LatLng>();
        lineOptions = new PolylineOptions();
        int c =1;
        List<HashMap<String, String>> path = routePath;

        // Fetching all the points in i-th route
        for(int j=0;j<path.size();j++) {
            HashMap<String, String> point = path.get(j);

            if (point.containsKey("distance")) {// Get distance from the list
//                    if(flag & c==1) {
//                        distance += Long.valueOf(point.get("distance").toString());
//                        c++;
//                    }
//                    else {
//                        flag = false;
                distance += Long.valueOf(point.get("distance").toString());
                //                    }
            } else if (point.containsKey("duration")) { // Get duration from the list
                duration += Long.valueOf(point.get("duration").toString());
            } else if (point.containsKey("lat") && point.containsKey("lng")) {
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

//                if(c==2 && flag) {
//                    lineOptions.addAll(points);
//                    lineOptions.width(10);
//                    if(c==2)
//                        lineOptions.color(Color.BLUE);
//                    else
//                        lineOptions.color(Color.RED);
//                    mMap.addPolyline(lineOptions);
//                    lineOptions = new PolylineOptions();
//                    points.clear();
//                    flag = false;
//                }
//                else
//                    points.add(position);
//            }
                points.add(position);
            }
        }
        //Adding all the points in the route to LineOptions
        lineOptions.addAll(points);
        lineOptions.width(5);
        lineOptions.color(Color.BLUE);
//        }
//        lblDistanceTime.setVisibility(View.VISIBLE);
        long hr = duration/3600;
        long reminder = duration%3600;
        long min = reminder/60;
        long sec = reminder%60;
        String time="";
        if(hr>0)
            time += hr+" hour ";
        if(min > 0)
            time += min+" min ";
        if(sec > 0)
            time += sec+" sec ";
        long km = (distance/1000);
        String dis="";
        if(km>0)
            dis = km+" km";
        else
            dis = distance+" m";
//        lblDistanceTime.setText("Distance:" + dis + " , Duration:" + time);

        // Drawing polyline in the Google Map for the i-th route
        mMap.addPolyline(lineOptions);
        MarkerOptions marker1 = new MarkerOptions();
        marker1 = new MarkerOptions().position(new LatLng(cLat, cLon)).title("You are here");
        marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        String[] latlng = userLatLng.split(",");
        MarkerOptions marker2 = new MarkerOptions();
        marker2 = new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]))).title("Customer Address");
        marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(marker1);
        mMap.addMarker(marker2);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cLat, cLon), 12.0f));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getRoute(routes.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMyLocationChange(Location location) {
//        if(marker!=null){
//            marker.remove();
//        }
//        markerlocation= new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You are here");
//        markerlocation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//        marker = mMap.addMarker(markerlocation);//new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You are Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).visible(true));
//        marker.showInfoWindow();
//        mMap.addMarker(markerlocation);

    }
}
