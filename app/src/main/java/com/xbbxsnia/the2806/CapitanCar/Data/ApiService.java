package com.xbbxsnia.the2806.CapitanCar.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("mysms.php")
    Call<SMS> sendSMS1(@Field("to_number") String to_number, @Field("verification_code") String code);
}
