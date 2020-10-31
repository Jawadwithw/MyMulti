package com.xbbxsnia.the2806.CapitanCar.Data;

import com.parse.ParseCloud;

import java.util.HashMap;

public class CreateOrderNotification {
    public CreateOrderNotification(){

    }
    public void createOrderNotification(String device_id,String message){
        HashMap<String, String> params = new HashMap();
        params.put("device_id",device_id);
        params.put("message", message);
        ParseCloud.callFunctionInBackground("sendChatPush", params);
    }
}
