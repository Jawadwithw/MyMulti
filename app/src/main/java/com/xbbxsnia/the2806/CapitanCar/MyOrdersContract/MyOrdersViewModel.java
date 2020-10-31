package com.xbbxsnia.the2806.CapitanCar.MyOrdersContract;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;

import java.util.List;


public class MyOrdersViewModel extends AndroidViewModel {
    public MyOrdersViewModel(@NonNull Application application) {
        super(application);
    }



    void getOrders(Context context, String user_name,int page,int limit, final OnParseObjectReceived onParseObjectReceived){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("tbl_"+user_name);
        query.orderByDescending("updatedAt");
        query.setLimit(limit);
        query.setSkip(page * limit);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    onParseObjectReceived.onReceived(objects);

                }
            }
        });
    }
}
