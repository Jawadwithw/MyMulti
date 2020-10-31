package com.xbbxsnia.the2806.CapitanCar.Data;

import retrofit2.Call;
import retrofit2.http.Field;

public interface DataSource {
    Call<SMS> sendSMS1(@Field("to_number") String to_number, @Field("verification_code") String code);


}
