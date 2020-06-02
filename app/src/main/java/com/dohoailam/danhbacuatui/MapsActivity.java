package com.dohoailam.danhbacuatui;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dohoailam.adapter.GoogleInfoAdapter;
import com.dohoailam.model.DanhBa;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    DanhBa danhBa = new DanhBa("tét","tui");
    Spinner spinnerMapType;
    ArrayAdapter<String> adapterSpinner;
    String [] arrMapType = {"Normal","Satellite","Terrain","Hybrid","None"};

    private GoogleMap mMap;
    GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(danhBa.getHo_ten()));
            mMap.setInfoWindowAdapter(new GoogleInfoAdapter((Activity) getApplicationContext(), danhBa));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            marker.showInfoWindow();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(onMyLocationChangeListener);

        addControls();
        addEvents();
    }

    private void addEvents() {
        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyChonMapType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void xuLyChonMapType(int i) {
        switch (i)
        {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 4:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
    }

    private void addControls() {
        spinnerMapType = findViewById(R.id.spinnerMapType);
        adapterSpinner = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                arrMapType
        );

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMapType.setAdapter(adapterSpinner);
        
    }


}
