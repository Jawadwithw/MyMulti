package com.xbbxsnia.the2806.Main2806.Main2806.MainContract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.MyCustomReceiver;

import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DataFakeGeneratorTab;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends BaseActivity implements BaseView {
    boolean doubleBackToExitPressedOnce = false;

    MyCustomReceiver customReceiver;
    private ImageView iv_tab_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        customReceiver = new MyCustomReceiver();
        IntentFilter intentFilter = new IntentFilter("com.parse.push.intent.RECEIVE");
        registerReceiver(customReceiver, intentFilter);
    }



    @Override
    public void setupViews() {


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_2806);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ArrayList<MenuAppData> tabList = DataFakeGeneratorTab.getMoreDatas();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setCustomView(R.layout.custom_tab);
            iv_tab_icon = (ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(i)).getCustomView()).findViewById(R.id.iv_tab_icon);
            iv_tab_icon.setImageResource(tabList.get(i).getFeatureImage());
        }
        ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_selected));
        Objects.requireNonNull(tabLayout.getTabAt(1)).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               switch (tab.getPosition()){
                   case 0:
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_frame_selected));
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_account_info));
                       break;
                   case 1:
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_frame));
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_selected));
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_account_info));
                       break;
                   case 2:
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_frame));
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
                       ((ImageView) Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getCustomView()).findViewById(R.id.iv_tab_icon)).setImageDrawable(getResources().getDrawable(R.drawable.ic_account_info_selected));
                       break;
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }

    public UserSharedPrefManager getSharedPref(Context context){
        return new UserSharedPrefManager(context);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customReceiver != null) {
            unregisterReceiver(customReceiver);
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(getViewContext(), "لطفا برای خروج دکمه بازگشت را دوباره فشار دهید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}