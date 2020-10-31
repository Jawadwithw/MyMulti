package com.xbbxsnia.the2806.CapitanCar.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefManager {
    private static final String USER_SHARED_PREF_NAME = "user_shared_pref";

    private SharedPreferences sharedPreferences;

    public UserSharedPrefManager(Context context){
        sharedPreferences=context.getSharedPreferences(USER_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setIsCooprate(boolean is_cooprate){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_cooprate",is_cooprate);
        editor.commit();
    }

    public void saveUserData(String key,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getUserData(String key){
        return sharedPreferences.getString(key,"");
    }

    public boolean getIsCooprate(){
        return sharedPreferences.getBoolean("is_cooprate",false);
    }


}
