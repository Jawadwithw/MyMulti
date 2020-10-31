package com.xbbxsnia.the2806.CapitanCar.PersonalInfoContract;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Data.OnModelsReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;

import java.util.ArrayList;
import java.util.List;


public class PersonalInfoViewModel extends AndroidViewModel {
    private DialogGenerator dialogGenerator;
    private Dialog progress_dialog;

    public PersonalInfoViewModel(@NonNull Application application) {
        super(application);
        dialogGenerator= new DialogGenerator();

    }

    void getBrands(Context context, final OnParseObjectReceived objectReceived) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Brands");
        progress_dialog = dialogGenerator.progressDialog(context);
        progress_dialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                progress_dialog.dismiss();
                if (e == null) {

                    objectReceived.onReceived(objects);
                }
                //    Log.d(TAG, "done: "+ objects.get(0).get("Name"));
            }
        });
    }


    void getModel(Context context, List<ParseObject> parseObjects, String brand, final OnModelsReceived modelsReceived) {
        AsyncTaskGetCarModel asyncTaskGetCarModel = new AsyncTaskGetCarModel();
        asyncTaskGetCarModel.getCarModel(context, parseObjects, brand, new OnModelsReceived() {
            @Override
            public void onReceived(ArrayList<String> models) {
                modelsReceived.onReceived(models);
            }
        });
        asyncTaskGetCarModel.execute();
    }

    private static class AsyncTaskGetCarModel extends AsyncTask<Void, Void, Void> {

        private Context context;
        private List<ParseObject> parseObjects;
        private String brand;
        private OnModelsReceived modelsReceived;
        private ArrayList<String> models = new ArrayList<>();

        private void getCarModel(Context context, List<ParseObject> parseObjects, String brand, OnModelsReceived modelsReceived) {
            this.context = context;
            this.parseObjects = parseObjects;
            this.brand = brand;
            this.modelsReceived = modelsReceived;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < parseObjects.size(); i++) {
                if (parseObjects.get(i).get("Brand").equals(brand)) {
                    models.add((String) parseObjects.get(i).get("Model"));
                }

            }
            modelsReceived.onReceived(models);
            return null;
        }
    }
}
