package com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract;

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
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.ZipJet.models.Laundry;
import com.xbbxsnia.the2806.ZipJet.models.LaundryComment;

import java.util.ArrayList;
import java.util.List;

public class LaundryDetailsViewModel extends AndroidViewModel {
    private List<LaundryComment> comments = new ArrayList<>();
    private DialogGenerator dialogGenerator;
    private Dialog progress_dialog;
    public LaundryDetailsViewModel(@NonNull Application application) {
        super(application);
        dialogGenerator= new DialogGenerator();

    }


    public void getComments(Context context,String cooperate_username, OnParseObjectReceived parseObjectReceived){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("cmm_"+cooperate_username);
        query.whereEqualTo(LaundryDetailsActivity.CMM_IS_CONFIRMED,true);
        progress_dialog = dialogGenerator.progressDialog(context);
        progress_dialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                progress_dialog.dismiss();
                if (e == null){

                    parseObjectReceived.onReceived(objects);
                }
            }
        });
    }

    public Laundry getLaundry(String code){
        Laundry laundry = new Laundry();
        laundry.setName("خشکشویی تست");
        laundry.setAddress("میدان کاج_بین خ نهم و هفتم_ ساختمان \\n میلاد کاج _ طبقه دوم _ واحد ");
        List<String> images = new ArrayList<>();
        images.add("https://picsum.photos/1920/1080?random=1");
        images.add("https://picsum.photos/1920/1080?random=2");
        images.add("https://picsum.photos/1920/1080?random=3");
        images.add("https://picsum.photos/1920/1080?random=4");
        laundry.setImagesUrl(images);
        laundry.setAvatarUrl("https://picsum.photos/1920/1080?random=5");
        laundry.setRate(4.5f);
        laundry.setPhoneNumber("09304154108");
        laundry.setRateNumbersPercent(new int[]{50, 70, 30, 80, 10});
        laundry.setComments(getComments(code));
        return laundry;
    }

    public List<LaundryComment> getComments(String code){
        for (int i = 0; i <= 5; i++) {
            LaundryComment comment = new LaundryComment();
            comment.setUserName("داوود افتخاری");
            comment.setRate((float) (i + 0.5));
            comment.setDate("1399/04/10");
            comment.setBody("کارشون عالیه");
            comments.add(comment);
        }
        return comments;
    }
}
