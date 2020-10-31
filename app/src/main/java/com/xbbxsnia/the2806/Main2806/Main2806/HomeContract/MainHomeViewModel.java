package com.xbbxsnia.the2806.Main2806.Main2806.HomeContract;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

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

import java.util.List;

public class MainHomeViewModel  extends AndroidViewModel {

    public MainHomeViewModel(@NonNull Application application) {
        super(application);

    }


    public void getDirectories(Context context, String directory_type, OnParseObjectReceived parseObjectReceived){

    AsyncTaskGetDirectories taskGetDirectories = new AsyncTaskGetDirectories();
    taskGetDirectories.getDirectories(context, directory_type, new OnParseObjectReceived() {
        @Override
        public void onReceived(List<ParseObject> parseObjects) {
            parseObjectReceived.onReceived(parseObjects);
        }
    });
    taskGetDirectories.execute();
    }


    private static class AsyncTaskGetDirectories extends AsyncTask<Void, Void, Void>{

        private Context context;
        private String directory_type;
        private OnParseObjectReceived parseObjectReceived;

        private void getDirectories(Context context, String directory_type, OnParseObjectReceived parseObjectReceived){
            this.context = context;
            this.directory_type = directory_type;

            this.parseObjectReceived = parseObjectReceived;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            //here i will set switch for other apps like zipjet..
            ParseQuery<ParseObject> query = ParseQuery.getQuery(directory_type);
            UserSharedPrefManager sharedPrefManager = new UserSharedPrefManager(context);
            String lat = sharedPrefManager.getUserData(OrderActivity.ADDRESS_LAT);
            String lang = sharedPrefManager.getUserData(OrderActivity.ADDRESS_LANG);
            if (!lat.equals("") && !lang.equals("") ){
                query.whereNear(ColleagueActivity.COOPERATE_JOB_LOCATION,new ParseGeoPoint(Double.parseDouble(lat),Double.parseDouble(lang)));
            }

            query.setLimit(4);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null){
                        parseObjectReceived.onReceived(objects);
                    }
                }
            });
            return null;
        }
    }
}