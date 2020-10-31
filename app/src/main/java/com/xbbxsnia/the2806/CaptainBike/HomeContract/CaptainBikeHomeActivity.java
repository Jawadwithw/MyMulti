package com.xbbxsnia.the2806.CaptainBike.HomeContract;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CaptainBike.ChooseSourceContract.ChooseSourceFragment;
import com.xbbxsnia.the2806.CaptainBike.ChooseTransportationVehicleContract.ChooseTransportationVehicleFragment;
import com.xbbxsnia.the2806.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class CaptainBikeHomeActivity extends BaseActivity {
    private HomeViewModel viewModel;
    private SupportMapFragment mapFragment;
    private LatLng my_location;
    private Bitmap markerIcon;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private FusedLocationProviderClient locationProviderClient;
    private Marker marker;
    private MaterialCardView cvCurrentLocation;
    private ImageView ivUserAvatar;
    private TextView userWalletValue;
    private MaterialCardView cvBackArrow;
    private FrameLayout flFragmentContainer;
    private State serviceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitan_bike_home);
        setupViews();
    }

    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        cvCurrentLocation = findViewById(R.id.cv_captainBikeHome_currentLocation);
        ivUserAvatar = findViewById(R.id.iv_captainBikeHome_userAvatar);
        userWalletValue = findViewById(R.id.tv_captainBikeHome_walletValue);
        cvBackArrow = findViewById(R.id.cv_captainBikeHome_backButton);
        flFragmentContainer = findViewById(R.id.fl_captainBikeHome_fragmentContainer);
        markerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pick);
        serviceState = new ChooseSourceState();
        serviceState.initFragment();

        cvCurrentLocation.setOnClickListener(v -> getDeviceLocation());
    }

    private void getDeviceLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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

    }

    private void moveCamera(LatLng latLang, float zoom,String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang, zoom));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fl_captainBikeHome_mapContainer);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            if (mLocationPermissionGranted) {
                getDeviceLocation();
                if (ActivityCompat.checkSelfPermission(CaptainBikeHomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CaptainBikeHomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions

                    return;
                }
                mMap.setMyLocationEnabled(true);
            }

            mMap.setOnMapClickListener(latLng -> {
                if (marker!=null){
                    marker.remove();
                }
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title("موقعیت من")
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_pick));
                my_location = latLng;

                marker = mMap.addMarker(options);

                GetGeoAsync getGeoAsync=new GetGeoAsync(latLng);
                getGeoAsync.execute();
            });

        });
    }

    public class GetGeoAsync extends AsyncTask<Void,Void,Void> {

        private LatLng latLng;

        public  GetGeoAsync(LatLng latLng){

            this.latLng = latLng;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Geocoder geocoder=new Geocoder(CaptainBikeHomeActivity.this, Locale.getDefault());

            try {
                List<Address> addressList=geocoder.getFromLocation(latLng.latitude,latLng.longitude,10);

                for(Address address:addressList){
                    Log.d("TAG","onMapClick:  "+address.getAddressLine(0));
                    Log.d("TAG","onMapClick:  "+address.getLocality());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onMapClick: "+e);
            }
            return null;
        }
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

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult3:  granted");

                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions((Activity) this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
                Log.d(TAG, "onRequestPermissionsResult2:  granted");

            }
        } else {
            Log.d(TAG, "onRequestPermissionsResult1:  granted");

            ActivityCompat.requestPermissions((Activity) this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
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
        int availble = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (availble == ConnectionResult.SUCCESS) {
            //every thing is fine and user can make map request
            Log.d(TAG, "isServicesOk: is Working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availble)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((Activity) this, availble, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "you cant take requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public interface State {
        void initFragment();
        void onBackClicked();
    }

    public class ChooseSourceState implements State {

        @Override
        public void initFragment() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_captainBikeHome_fragmentContainer, new ChooseSourceFragment(() -> {
                serviceState = new ChooseTransportationVehicleState();
                serviceState.initFragment();
            }));
            transaction.commit();
        }

        @Override
        public void onBackClicked() {
            CaptainBikeHomeActivity.this.onBackPressed();
        }
    }

    public class ChooseTransportationVehicleState implements State {

        @Override
        public void initFragment() {
            ChooseTransportationVehicleFragment fragment = new ChooseTransportationVehicleFragment();
            fragment.show(getSupportFragmentManager(), "chooseTransportationVehicle");
        }

        @Override
        public void onBackClicked() {
            serviceState = new ChooseSourceState();
        }
    }

}