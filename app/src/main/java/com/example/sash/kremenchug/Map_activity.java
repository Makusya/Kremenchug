package com.example.sash.kremenchug;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Map_activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    GoogleMap mMap;
    private Double latitude;
    private Double longitude;
    TextView tvm;
    Button btn_save_coor;

    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);

        btn_save_coor = (Button) findViewById(R.id.btn_save_coor);
        btn_save_coor.setOnClickListener(this);
    }

       private void moveMap() {
               LatLng latLng = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().isZoomControlsEnabled();
        mMap.getMinZoomLevel();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng kremenchug = new LatLng(49.0680200, 33.4204100);

        Marker MO =  mMap.addMarker(new MarkerOptions()
                .position(kremenchug)
                .draggable(true)
        );

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker M0) {  }

            @Override
            public void onMarkerDragEnd(Marker M0) {

                LatLng pos = M0.getPosition();
                latitude = M0.getPosition().latitude;
                longitude = M0.getPosition().longitude;


                tvm = (TextView) findViewById(R.id.textView11);
                tvm.setText(latitude.toString()+" "+longitude.toString());

                moveMap();
            }

            @Override
            public void onMarkerDrag(Marker M0) {     }

        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kremenchug));

    }

       @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.btn_save_coor){
                intent.putExtra("coordinate", latitude.toString()+" "+longitude.toString());
        }
        setResult(RESULT_OK,intent);
        finish();

    }


}
