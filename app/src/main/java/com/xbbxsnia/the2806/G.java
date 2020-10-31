package com.xbbxsnia.the2806;


import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.parse.GetCallback;
import com.parse.Parse;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.fcm.ParseFCM;
import com.xbbxsnia.the2806.CapitanCar.Data.Message;


public class G extends MultiDexApplication {
    public static ParseUser currentUser;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.my_parse))
                .clientKey("myKey")
                .server(getString(R.string.parse_server))
                .enableLocalDataStore()
                .build()

        );

        currentUser = ParseUser.getCurrentUser();


        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
          //      ParseFCM.register(getApplicationContext());
            }
        });
    }
}
