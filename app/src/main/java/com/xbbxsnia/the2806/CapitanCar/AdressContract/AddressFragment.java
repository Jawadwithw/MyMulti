package com.xbbxsnia.the2806.CapitanCar.AdressContract;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class AddressFragment extends BaseFragment implements BaseView {
    private Button btn_map, btn_input_address;
    private View map_line, address_input_line;
    private RelativeLayout layout_map, layput_input_address;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private LatLng my_location;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FusedLocationProviderClient locationProviderClient;
    private SupportMapFragment mapFragment;
    private EditText et_address,et_street,et_extra_address,et_state,et_city,et_postal_code;
    private Marker marker;

    @Override
    public void setupViews() {
        et_address = (EditText) rootView.findViewById(R.id.et_address);
        et_street = (EditText) rootView.findViewById(R.id.et_street);
        et_extra_address = (EditText) rootView.findViewById(R.id.extra_address);
        et_state = (EditText) rootView.findViewById(R.id.et_state);
        et_city = (EditText) rootView.findViewById(R.id.et_city);
        et_postal_code = (EditText) rootView.findViewById(R.id.et_postal_code);

        btn_map = (Button) rootView.findViewById(R.id.btn_map_view);
        btn_input_address = (Button) rootView.findViewById(R.id.btn_input_address);

        et_address.setText(((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager().getUserData(OrderActivity.ADDRESS));

        map_line = (View) rootView.findViewById(R.id.map_view_line);
        address_input_line = (View) rootView.findViewById(R.id.input_address_line);

        layout_map = (RelativeLayout) rootView.findViewById(R.id.map_view);
        layput_input_address = (RelativeLayout) rootView.findViewById(R.id.input_adress_view);

        btn_input_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address_input_line.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                map_line.setBackgroundColor(Color.GRAY);
                layout_map.setVisibility(View.GONE);
                layput_input_address.setVisibility(View.VISIBLE);

            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                address_input_line.setBackgroundColor(Color.GRAY);
                map_line.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                layput_input_address.setVisibility(View.GONE);
                layout_map.setVisibility(View.VISIBLE);

            }
        });
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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
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


                        GetGeoAsync getGeoAsync=new GetGeoAsync(latLng);
                        getGeoAsync.execute();
                    }
                });

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


    @Override
    public int getLayout() {
        return R.layout.layout_address;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        AddressFragment newFragment = new AddressFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        String address=et_address.getText().toString().trim();
        String city=et_city.getText().toString().trim();
        String ex_address=et_extra_address.getText().toString().trim();
        String state=et_state.getText().toString().trim();
        String street=et_street.getText().toString().trim();
        String total_address = address + " -شهر: "+city+"- آدرس اضافی: "+ ex_address +"- استان: "+ state+"- خیابان: "+ street;
        String lat = String.valueOf(my_location.latitude);
        String lang = String.valueOf(my_location.longitude);
        if (address.equals("")){
            Toast.makeText(context, getResources().getString(R.string.toast_fill_address), Toast.LENGTH_SHORT).show();
            return false;

        }else {
            UserSharedPrefManager prefManager=((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager();
            prefManager.saveUserData(OrderActivity.ADDRESS_LAT,lat);
            prefManager.saveUserData(OrderActivity.ADDRESS_LANG,lang);
            prefManager.saveUserData(OrderActivity.ADDRESS,address);
            prefManager.saveUserData(OrderActivity.TOTAL_ADDRESS,total_address);
            return true;
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


    public class GetGeoAsync extends AsyncTask<Void,Void,Void>{

        private LatLng latLng;

        public  GetGeoAsync(LatLng latLng){

            this.latLng = latLng;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Geocoder geocoder=new Geocoder(getViewContext(), Locale.getDefault());

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

}
