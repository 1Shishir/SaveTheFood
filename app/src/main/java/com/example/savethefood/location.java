package com.example.savethefood;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class location extends AppCompatActivity {
    SupportMapFragment smf;
    FusedLocationProviderClient client;
    private GoogleMap mMap;
    LocationManager locationManager;
    Button getDir;
    Boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        getDir=findViewById(R.id.btnGetDirection);

        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                        mode=getIntent().getBooleanExtra("mode",true);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }


    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        MarkerOptions markerOptions1=null;
                        MarkerOptions markerOptions = null;

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        LatLng latLng1 = null;

                        Log.d("maps", String.valueOf(latLng.latitude)+" "+String.valueOf(latLng.longitude));

                        String dest=getIntent().getStringExtra("rest_loc");

                        //convert to latitute

                        Geocoder geocoder = new Geocoder(com.example.savethefood.location.this);
                        List<Address> addresses = null;

                        try {
                            // Get the address location
                            addresses = geocoder.getFromLocationName(dest, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (addresses != null && !addresses.isEmpty()) {
                            double latitude = addresses.get(0).getLatitude();
                            double longitude = addresses.get(0).getLongitude();

                            latLng1 = new LatLng(latitude,longitude);
                            markerOptions = new MarkerOptions().position(latLng).title("Me");
                            if(mode){
                                markerOptions1 = new MarkerOptions().position(latLng1).title("Customer");

                            }
                            else {
                                markerOptions1 = new MarkerOptions().position(latLng1).title("Food");
                            }
                       Marker map_src= googleMap.addMarker(markerOptions);
                        Marker map_dest=googleMap.addMarker(markerOptions1);

                        map_src.showInfoWindow();
                        map_dest.showInfoWindow();

                        LatLngBounds bounds = new LatLngBounds.Builder()
                                .include(markerOptions.getPosition())
                                .include(markerOptions1.getPosition())
                                .build();
//

                        String origin = markerOptions.getPosition().latitude + "," + markerOptions.getPosition().longitude;
                        String destination = markerOptions1.getPosition().latitude + "," + markerOptions1.getPosition().longitude;

                        int padding = 150;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cameraUpdate);
                        }
                        else {
                            Toast.makeText(location.this, "Address Not found", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        MarkerOptions finalMarkerOptions = markerOptions;
                        MarkerOptions finalMarkerOptions1 = markerOptions1;

                        getDir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                googleMapIntent(finalMarkerOptions, finalMarkerOptions1);
                            }
                        });




                    }
                });
            }
        });
    }

    private void googleMapIntent(MarkerOptions markerSrc,MarkerOptions markerDest) {
        String origin = String.valueOf(markerSrc.getPosition().latitude)+","+String.valueOf(markerSrc.getPosition().longitude);
        String destination =String.valueOf(markerDest.getPosition().latitude)+","+String.valueOf(markerDest.getPosition().longitude);
        String uriString = "http://maps.google.com/maps?saddr=" + origin + "&daddr=" + destination;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uriString));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }


}

