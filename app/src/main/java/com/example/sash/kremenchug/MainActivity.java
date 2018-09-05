package com.example.sash.kremenchug;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    final int menu_doit = 1;
    final int menu_doit2 = 2;
    final int menu_doit3 = 3;
    final int menu_exit = 6;

    MapFragment mapFragment;
    Button button_create;
    Button button_active;
    Button button_done;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_create = (Button) findViewById(R.id.Report_btn);
        button_create.setOnClickListener(this);
        button_active = (Button) findViewById(R.id.Active_btn);
        button_active.setOnClickListener(this);
        button_done = (Button) findViewById(R.id.Done_btn);
        button_done.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        createMapView();
    }

        private void createMapView() {
          try {
            if (null == mapFragment) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

                if (null == mapFragment) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception) {
            Log.e("mapApp", exception.toString());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        LatLng kremenchug = new LatLng(49.0680200, 33.4204100);
        googleMap.addMarker(new MarkerOptions()
                .position(kremenchug)
                .title("Marker in Kremenchug"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kremenchug));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.069, 33.421))
                .title("Problem with water"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.070, 33.422))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Problem with air"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.0672, 33.4232))
                .title("Problem with money"));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.066, 33.424))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Problem with power"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, menu_doit, 0, "Report on");
        menu.add(0, menu_doit2, 0, "Sign in");
        menu.add(0, menu_doit3, 0, "Registration");
        menu.add(6, menu_exit, 0, "Exit");

        return true;
    }

    private void signOut() {
        mAuth.signOut();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.how_it_work) {
            Intent intent = new Intent(this, Activity_about_work.class);
            startActivity(intent);
            return true;
        }
        else if (id == menu_doit){
            Intent intent = new Intent(this, Activity_massage.class);
            startActivity(intent);
        }
        else if (id == menu_doit3){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        else if (id == menu_exit){
            signOut();
        }
        else if (id == menu_doit2){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.statistics){
            Intent intent = new Intent(this, Activity_statistic.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

       if (id == R.id.Report_btn){
            Intent intent = new Intent(this, Activity_massage.class);
            startActivity(intent);
        }
        else if (id == R.id.Active_btn){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.Done_btn){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }



}
