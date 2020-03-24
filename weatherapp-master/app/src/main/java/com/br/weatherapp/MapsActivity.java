package com.br.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.util.Log;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.br.weatherapp.presenter.CityPresenter;
import com.br.weatherapp.presenter.CityPresenterImpl;
import com.br.weatherapp.view.ICityView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import com.br.weatherapp.model.City;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ICityView {

    private final static String[] REQUIRED_PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION };

    @BindView(R.id.recyclerCityView)
    RecyclerView recyclerCityView;
    @BindView(R.id.searchButton)
    FloatingActionButton searchButton;

    private static final String TAG = MapsActivity.class.getSimpleName();
    private boolean locationPermissionGranted;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;


    private CityAdapter cityAdapter;
    private ArrayList<City> cities = new ArrayList<>();
    private CityPresenter mCityPresenter;

    private LatLng lastLocation;
    private MapsActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = this;
        ButterKnife.bind(activity);
        initializeViews();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(activity);


        final boolean askIfDenied = true; // Will prompt the user if he has previously denied the permission

    }

    private void initializeViews() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCityView.setLayoutManager(layoutManager);
        refreshList();
        mCityPresenter = new CityPresenterImpl(this);

    }

    private void refreshList() {
        cityAdapter = new CityAdapter(this.getApplicationContext(),this, cities);
        recyclerCityView.setAdapter(cityAdapter);
    }

    @Override
    public void setItems(ArrayList<City> items) {
        cities.clear();
        cities.addAll(items);
        refreshList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();

        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                lastLocation = new LatLng(task.getResult().getLatitude(),
                                        task.getResult().getLongitude());
                            } else {
                                lastLocation = new LatLng(-34, 151);
                            }
                            mMap.addMarker(new MarkerOptions().position(lastLocation).title("City"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLocation));// Set the map's camera position to the current location of the device.
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                        }
                        // Got last known location. In some rare situations this can be null.

                    }
                });

        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                lastLocation = point;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point).title("City"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityPresenter.onSearchStarted(lastLocation.latitude, lastLocation.longitude);
            }
        });
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if(requestCode == 1) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        }
    }

}