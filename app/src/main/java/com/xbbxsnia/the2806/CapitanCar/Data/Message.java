package com.xbbxsnia.the2806.CapitanCar.Data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String FROM_KEY = "msg_from";
    public static final String TO_KEY = "msg_to";
    public static final String BODY_KEY = "body";

    public static String getIMAGE_FileName_KEY() {
        return IMAGE_FileName_KEY;
    }

    public static final String IMAGE_FileName_KEY = "image_file_name";


    public String getBody() {
        return getString(BODY_KEY);
    }


    public void setBody(String body) {
        put(BODY_KEY, body);
    }


    public static String getFromKey() {
        return FROM_KEY;
    }

    public static String getToKey() {
        return TO_KEY;
    }
}