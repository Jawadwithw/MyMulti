package com.xbbxsnia.the2806.CapitanCar.Data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerDataSource implements DataSource {
    private ApiService apiService;

    public ServerDataSource(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.jawadabbasnia.ir/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public Call<SMS> sendSMS1(String to_number, String code) {
        return apiService.sendSMS1(to_number,code);
    }
}
