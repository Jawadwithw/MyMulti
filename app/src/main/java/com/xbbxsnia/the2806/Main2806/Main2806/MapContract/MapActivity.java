package com.xbbxsnia.the2806.Main2806.Main2806.MapContract;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.R;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class MapActivity extends BaseActivity implements BaseView {
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Button btn_confirm;
    private Intent intent;
    private LatLng my_location;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FusedLocationProviderClient locationProviderClient;
    private SupportMapFragment mapFragment;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setupViews();
    }

    @Override
    public void setupViews() {
        btn_confirm = (Button)findViewById(R.id.btn_confirm_map);

        intent = getIntent();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("lat",my_location.latitude);
                intent.putExtra("long",my_location.longitude);
                setResult(RESULT_OK,intent);
                finish();
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



    @Override
    public Context getViewContext() {
        return this;
    }

    private void getDeviceLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getViewContext());
        try {
            if ((mLocationPermissionGranted)) {
                final Task<Location> location = locationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete:  found location");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "موقعیت من");
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }


        if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    private void moveCamera(LatLng latLang, float zoom,String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLang)
                .title(title);
        my_location = latLang;
        marker = mMap.addMarker(options);


    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_activity);
        assert mapFragment != null;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (mLocationPermissionGranted) {
                    getDeviceLocation();
                    if (ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions

                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if (marker!=null){
                            marker.remove();
                        }
                        MarkerOptions options = new MarkerOptions()
                                .position(latLng)
                                .title("موقعیت من");
                        my_location = latLng;



                        marker = mMap.addMarker(options);


                    }
                });

            }
        });
    }


    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getViewContext().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getViewContext().getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult3:  granted");

                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions((Activity) getViewContext(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "onRequestPermissionsResult2:  granted");

            }
        } else {
            Log.d(TAG, "onRequestPermissionsResult1:  granted");

            ActivityCompat.requestPermissions((Activity) getViewContext(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
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
        int availble = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getViewContext());
        if (availble == ConnectionResult.SUCCESS) {
            //every thing is fine and user can make map request
            Log.d(TAG, "isServicesOk: is Working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availble)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((Activity) getViewContext(), availble, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getViewContext(), "you cant take requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}