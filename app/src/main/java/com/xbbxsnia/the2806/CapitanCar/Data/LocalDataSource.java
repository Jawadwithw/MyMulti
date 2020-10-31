package com.xbbxsnia.the2806.CapitanCar.Data;

import android.app.Application;

import retrofit2.Call;

public class LocalDataSource implements DataSource {

    public LocalDataSource (Application application){

    }
    @Override
    public Call<SMS> sendSMS1(String to_number, String code) {
        return null;
    }
}
