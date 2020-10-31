package com.xbbxsnia.the2806.CapitanCar.ProblemInfoContract;

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
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;

import java.util.List;

public class ProblemInfoViewModel extends AndroidViewModel {
    private DialogGenerator dialogGenerator;
    private Dialog progress_dialog;    public ProblemInfoViewModel(@NonNull Application application) {
        super(application);

    }

    void getProblemType(Context context, final OnParseObjectReceived parseObjectReceived) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Problems");
        dialogGenerator = new DialogGenerator();
        progress_dialog = dialogGenerator.progressDialog(context);
        progress_dialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    progress_dialog.dismiss();
                    parseObjectReceived.onReceived(objects);

                }
            }
        });

    }
}
