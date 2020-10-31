package com.xbbxsnia.the2806.Main2806.Main2806.HomeContract;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.daimajia.slider.library.SliderLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.DirectoryContract.DirectoryActivity;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.Main2806.Main2806.MainContract.MainActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.MapContract.MapActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.LaundryAdapter;
import com.xbbxsnia.the2806.ZipJet.Adapters.LaundrySliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends BaseFragment implements BaseView {
    public static final int REQUEST_CODE = 2;
    private CardView iv_captan_car, iv_captan_fruit, iv_captan_bike, iv_zip_jet;
    private SliderLayout sliderLayout;
    private TextView tv_your_address, tv_address_1;
    private SliderView sliderView;
    private ArrayList<String> images_url = new ArrayList<>();
    private RecyclerView rv_landuries, rv_captain_car;
    private MainHomeViewModel mainHomeViewModel;
    private DialogGenerator dialogGenerator;
    private Dialog progress_dialog;
    private LaundryAdapter laundryAdapter;
    private LaundryAdapter captain_car_adapter;
    private HomeAdapter captainAdapter,landury_adapter;

    @Override
    public void setupViews() {
        dialogGenerator = new DialogGenerator();
        progress_dialog = dialogGenerator.progressDialog(getViewContext());
        iv_captan_car =  rootView.findViewById(R.id.iv_captan_car);
        iv_zip_jet =  rootView.findViewById(R.id.iv_zipjet);
        tv_your_address = (TextView) rootView.findViewById(R.id.tv_main_address);
        tv_address_1 = (TextView) rootView.findViewById(R.id.tv_address1);

        rv_landuries = rootView.findViewById(R.id.rv_landuries);
        rv_captain_car = rootView.findViewById(R.id.rv_captain_main);

        rv_landuries.setLayoutManager(new LinearLayoutManager(getViewContext(), RecyclerView.HORIZONTAL, true));
        rv_captain_car.setLayoutManager(new LinearLayoutManager(getViewContext(), RecyclerView.HORIZONTAL, true));
//        laundryAdapter = new LaundryAdapter();
//        captain_car_adapter = new LaundryAdapter();
        captainAdapter = new HomeAdapter();
        landury_adapter = new HomeAdapter();

        tv_address_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getViewContext(), MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        iv_captan_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getViewContext(), OrderActivity.class);
              //  intent.putExtra(ColleagueActivity.COOPERATE_TYPE, ColleagueActivity.CAPTAIN_CAR);
                startActivity(intent);

            }
        });
        iv_zip_jet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getViewContext(), com.xbbxsnia.the2806.ZipJet.OrderContract.OrderActivity.class);
              //  intent.putExtra(ColleagueActivity.COOPERATE_TYPE, ColleagueActivity.ZIP_JET);
                startActivity(intent);
            }
        });
        sliderView = (SliderView) rootView.findViewById(R.id.slider);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("tbl_ads");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < objects.size(); i++) {
                        images_url.add(objects.get(i).getString("image_url"));
                    }
                    sliderView.setSliderAdapter(new LaundrySliderAdapter(images_url, getViewContext()));
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setIndicatorSelectedColor(Color.WHITE);
                    sliderView.setIndicatorUnselectedColor(Color.GRAY);
                    sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                    sliderView.startAutoCycle();
                }
            }
        });

        ArrayList<MenuAppData> all_data = new ArrayList<>();
        all_data.addAll(DataGeneratorHome.getDatas(getViewContext()));
        ArrayList<MenuAppData> captainCar_List = new ArrayList<>();
        ArrayList<MenuAppData> zipjet_list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            captainCar_List.add(all_data.get(i));
        }

        for (int i = 3; i < all_data.size(); i++) {
            zipjet_list.add(all_data.get(i));
        }

        captainAdapter.setDatas(captainCar_List,getViewContext(),ColleagueActivity.CAPTAIN_CAR);
        rv_captain_car.setAdapter(captainAdapter);
        landury_adapter.setDatas(zipjet_list,getViewContext(),ColleagueActivity.ZIP_JET);
        rv_landuries.setAdapter(landury_adapter);
//        mainHomeViewModel = ViewModelProviders.of(this).get(MainHomeViewModel.class);
//        captain_car_adapter.startShimmer();
//        rv_captain_car.setAdapter(captain_car_adapter);
//        laundryAdapter.startShimmer();
//        rv_landuries.setAdapter(laundryAdapter);
//        mainHomeViewModel.getDirectories(getViewContext(), ColleagueActivity.CAPTAIN_CAR, new OnParseObjectReceived() {
//            @Override
//            public void onReceived(List<ParseObject> parseObjects) {
//                captain_car_adapter.setDirectories(parseObjects,ColleagueActivity.CAPTAIN_CAR, getViewContext());
//                //  rv_captain_car.setAdapter(captain_car_adapter);
//                getZipjetDirectories();
//            }
//        });




    }

    private void getZipjetDirectories() {

        mainHomeViewModel.getDirectories(getViewContext(), ColleagueActivity.ZIP_JET, new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                laundryAdapter.setDirectories(parseObjects, ColleagueActivity.ZIP_JET,getViewContext());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String address = ((MainActivity) Objects.requireNonNull(getActivity())).getSharedPref(getViewContext()).getUserData(OrderActivity.ADDRESS_LAT);
        if (!address.equals("")) {
            // tv_address_1.setVisibility(View.VISIBLE);
            tv_your_address.setText("آدرس شما ثبت شده است");
        } else {
            tv_your_address.setText(getResources().getString(R.string.tv_your_address_2));
        }


    }


    @Override
    public int getLayout() {
        return R.layout.layout_home_2806;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment newFragment = new HomeFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return false;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

                double lat = data.getDoubleExtra("lat", 0);
                double lang = data.getDoubleExtra("long", 0);
                ((MainActivity) Objects.requireNonNull(getActivity())).getSharedPref(getViewContext()).saveUserData(OrderActivity.ADDRESS_LAT, String.valueOf(lat));
                ((MainActivity) Objects.requireNonNull(getActivity())).getSharedPref(getViewContext()).saveUserData(OrderActivity.ADDRESS_LANG, String.valueOf(lang));
                tv_your_address.setText("آدرس شما ثبت شده است");

            }
        } catch (Exception ex) {
            Toast.makeText(getViewContext(), ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
