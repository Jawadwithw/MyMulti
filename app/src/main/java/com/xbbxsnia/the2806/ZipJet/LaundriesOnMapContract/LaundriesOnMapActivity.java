package com.xbbxsnia.the2806.ZipJet.LaundriesOnMapContract;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.LaundriesOnMapAdapter;
import com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract.LaundryDetailsActivity;
import com.xbbxsnia.the2806.ZipJet.models.Laundry;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class LaundriesOnMapActivity extends BaseActivity {
    private LaundriesOnMapViewModel viewModel;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private GoogleMap mMap;
    private FusedLocationProviderClient locationProviderClient;
    private SupportMapFragment mapFragment;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundries_on_map);
        setupViews();
    }

    private void getDeviceLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(LaundriesOnMapActivity.this);
        try {
            if ((mLocationPermissionGranted)) {
                final Task<Location> location = locationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete:  found location");
                        Location currentLocation = task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM,"موقعیت من");
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    private void moveCamera(LatLng latLang, float zoom,String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLang)
                .title(title);
        marker = mMap.addMarker(options);


    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.laundriesOnMap_mapFragment);
        assert mapFragment != null;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (mLocationPermissionGranted) {
                    if (ActivityCompat.checkSelfPermission(LaundriesOnMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LaundriesOnMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions

                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isServicesOk()) {

            getLocationPermission();

        }
        if (mLocationPermissionGranted){
            mapFragment.onResume();


        }
    }

    @Override
    public void onPause() {

        super.onPause();
        if (mLocationPermissionGranted){
            mapFragment.onPause();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mLocationPermissionGranted){
            mapFragment.onDestroy();

        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mLocationPermissionGranted){
            mapFragment.onLowMemory();

        }
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(LaundriesOnMapActivity.this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(LaundriesOnMapActivity.this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult3:  granted");

                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(LaundriesOnMapActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "onRequestPermissionsResult2:  granted");

            }
        } else {
            Log.d(TAG, "onRequestPermissionsResult1:  granted");

            ActivityCompat.requestPermissions(LaundriesOnMapActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            Log.d(TAG, "onRequestPermissionsResult1 2:  granted");


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult:  getting permission");

        mLocationPermissionGranted = false;
        Log.d(TAG, "onRequestPermissionsResult: "+ requestCode);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult:  failded");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult:  granted");

                    mLocationPermissionGranted = true;
                    //initlize our map
                    initMap();
                }
            }
        }
    }

    public boolean isServicesOk() {
        Log.d(TAG, "isServicesOk: ");
        int availble = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LaundriesOnMapActivity.this);
        if (availble == ConnectionResult.SUCCESS) {
            //every thing is fine and user can make map request
            Log.d(TAG, "isServicesOk: is Working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availble)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(LaundriesOnMapActivity.this, availble, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(LaundriesOnMapActivity.this, "you cant take requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    
    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(LaundriesOnMapActivity.this).get(LaundriesOnMapViewModel.class);
        CardView cvCurrentLocation = findViewById(R.id.cv_laundriesOnMap_currentLocation);
        CardView cvBackArrow = findViewById(R.id.cv_laundriesOnMap_backArrow);
        cvBackArrow.setOnClickListener(v -> onBackPressed());
        cvCurrentLocation.setClickable(true);
        initRecyclerView();
        cvCurrentLocation.setOnClickListener(v -> {
            getDeviceLocation();
        });
    }

    public void initRecyclerView () {
        RecyclerView rvLaundries = findViewById(R.id.rv_laundriesOnMapActivity_laundriesDirectory);
        rvLaundries.setLayoutManager(new LinearLayoutManager(LaundriesOnMapActivity.this, RecyclerView.HORIZONTAL, true));
        rvLaundries.setAdapter(new LaundriesOnMapAdapter(viewModel.getLaundries(), code -> {
            Intent intent = new Intent(LaundriesOnMapActivity.this, LaundryDetailsActivity.class);
            intent.putExtra("code", code);
            startActivity(intent);
        }));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvLaundries);
    }
}