package com.xbbxsnia.the2806.Main2806.Main2806.OrdersContract;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;

import java.util.List;


public class MyOrdersViewModel extends AndroidViewModel {
    private Dialog dialog;
    private DialogGenerator dialogGenerator;
    public MyOrdersViewModel(@NonNull Application application) {
        super(application);
    }



    void getOrders(Context context, String user_name,int page,int limit, final OnParseObjectReceived onParseObjectReceived){
      //  dialog = dialogGenerator.progressDialog(context);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("tbl_"+user_name);
        query.orderByDescending("updatedAt");
        query.whereEqualTo(OrderActivity.IS_MY_ORDER,true);
        query.setLimit(limit);
        query.setSkip(page * limit);
      //  dialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                //    dialog.dismiss();
                    onParseObjectReceived.onReceived(objects);

                }
            }
        });
    }
}
