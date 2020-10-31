package com.xbbxsnia.the2806.CapitanCar.Data;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;



public class InstallPush {
    public void installPush(final String user_name, final OnParseExceptionReceived exceptionReceived){
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("device_id", user_name);
        installation.put("chat_notific", true);
        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                exceptionReceived.onReceieved(e);
            }
        });



    }
}
