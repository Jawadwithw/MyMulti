package com.xbbxsnia.the2806.Main2806.Main2806.AllContract;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;

import java.util.List;

public class AllOrdersViewModel extends AndroidViewModel {
    public AllOrdersViewModel(@NonNull Application application) {
        super(application);
    }


    void getOrders(Context context, String app_name, int page, int limit, final OnParseObjectReceived onParseObjectReceived){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("order_"+app_name);
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
