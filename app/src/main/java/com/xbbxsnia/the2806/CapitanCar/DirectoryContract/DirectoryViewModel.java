package com.xbbxsnia.the2806.CapitanCar.DirectoryContract;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;

import java.util.List;

public class DirectoryViewModel extends AndroidViewModel {
    private DialogGenerator dialogGenerator;
    private Dialog progress_dialog;

    public DirectoryViewModel(@NonNull Application application) {
        super(application);
        dialogGenerator = new DialogGenerator();

    }


    public void getDirectories(Context context, String directory_type, OnParseObjectReceived parseObjectReceived) {
        //here i will set switch for other apps like zipjet..
        ParseQuery<ParseObject> query = ParseQuery.getQuery(directory_type);
        UserSharedPrefManager sharedPrefManager = new UserSharedPrefManager(context);
        String lat = sharedPrefManager.getUserData(OrderActivity.ADDRESS_LAT);
        String lang = sharedPrefManager.getUserData(OrderActivity.ADDRESS_LANG);
        if (!lat.equals("") && !lang.equals("")) {
            query.whereNear(ColleagueActivity.COOPERATE_JOB_LOCATION, new ParseGeoPoint(Double.parseDouble(lat), Double.parseDouble(lang)));
        }

        progress_dialog = dialogGenerator.progressDialog(context);
       // progress_dialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
             //   progress_dialog.dismiss();

                if (e == null) {
                    parseObjectReceived.onReceived(objects);
                }

            }
        });

    }
}
