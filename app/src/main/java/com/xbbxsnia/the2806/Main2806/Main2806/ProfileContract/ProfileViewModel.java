package com.xbbxsnia.the2806.Main2806.Main2806.ProfileContract;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.OnUserDataFetched;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.UserAppData;

public class ProfileViewModel extends AndroidViewModel {
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }


    public void getProfileData(Context context, OnUserDataFetched userDataFetched) {
        UserSharedPrefManager sharedPrefManager = new UserSharedPrefManager(context);
        UserAppData userAppData = new UserAppData();
        userAppData.setName(sharedPrefManager.getUserData(OrderActivity.NAME));
        userAppData.setLast_name(sharedPrefManager.getUserData(OrderActivity.LAST_NAME));
        userAppData.setAddress(sharedPrefManager.getUserData(OrderActivity.ADDRESS));
        if (G.currentUser != null) {
            userAppData.setUser_name(G.currentUser.getUsername());
        }
        userAppData.setGender_id(sharedPrefManager.getUserData(OrderActivity.GENDER_ID));
        userDataFetched.userDataFetched(userAppData);
    }
}
