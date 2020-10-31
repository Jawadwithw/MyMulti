package com.xbbxsnia.the2806.ZipJet.HomeContract;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.xbbxsnia.the2806.ZipJet.models.Laundry;
import com.xbbxsnia.the2806.ZipJet.models.User;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private List<Laundry> laundries = new ArrayList<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Laundry> getLaundries() {
        for (int i = 0; i <= 10; i++) {
            Laundry laundry = new Laundry();
            List<String> images = new ArrayList<>();
            images.add("https://picsum.photos/1920/1080?random=1");
            images.add("https://picsum.photos/1920/1080?random=2");
            images.add("https://picsum.photos/1920/1080?random=3");
            images.add("https://picsum.photos/1920/1080?random=4");
            laundry.setImagesUrl(images);
            laundry.setRate((float) (i + 0.5));
            laundry.setName(" خشکشویی " + i);
            laundry.setDistance(i + 1);
            laundry.setAvatarUrl("https://picsum.photos/500/500?random=1");
            laundry.setAddress("تهران ، سعادت آباد میدان کاج...");
            laundries.add(laundry);
        }
        return laundries;
    }

    public User getUser() {
        User user = new User();
        user.setUserName("داوود افتخاری");
        user.setUserPhoneNumber("09304154108");
        user.setUserBudget(50000);
        user.setUserAvatar("https://upload.wikimedia.org/wikipedia/commons/4/48/Outdoors-man-portrait_%28cropped%29.jpg");
        user.setUserAddress("تهران , سعادت آباد , میدان کاج , بین خیابان نهم و هفتم , ساختمان میلاد کاج , طبقه دوم , واحد 206");
        return user;
    }
}
