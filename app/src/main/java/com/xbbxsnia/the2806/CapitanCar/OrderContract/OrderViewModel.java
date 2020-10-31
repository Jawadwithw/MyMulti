package com.xbbxsnia.the2806.CapitanCar.OrderContract;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.G;

public class OrderViewModel extends AndroidViewModel {
    public OrderViewModel(@NonNull Application application) {
        super(application);
    }



    public void getUserData(final Context context, final ParseObjectGot parseObjectGot){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
        query.fromLocalDatastore();
        query.whereEqualTo("userName", String.valueOf(G.currentUser.getUsername()));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if ((e == null)){
                   parseObjectGot.objectGot(object);

                }else{
                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
