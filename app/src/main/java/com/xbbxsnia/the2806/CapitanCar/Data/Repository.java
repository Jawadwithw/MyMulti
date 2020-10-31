package com.xbbxsnia.the2806.CapitanCar.Data;

import android.app.Application;

import retrofit2.Call;

public class Repository implements DataSource {
    private LocalDataSource localDataSource;
    private ServerDataSource serverDataSource;
    public Repository(Application application){
        localDataSource = new LocalDataSource(application);
        serverDataSource = new ServerDataSource();
    }

    @Override
    public Call<SMS> sendSMS1(String to_number, String code) {
        return serverDataSource.sendSMS1(to_number,code);
    }
}
