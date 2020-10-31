package com.xbbxsnia.the2806;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.Main2806.Main2806.MainContract.MainActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.StartContract.StartActivity;

public class SplashActivity extends BaseActivity implements BaseView {
    private final int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(G.currentUser == null){
                    startActivity(new Intent(getViewContext(), StartActivity.class));
                }else {
                    startActivity(new Intent(getViewContext(), MainActivity.class));
                }
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void setupViews() {

    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
