package com.xbbxsnia.the2806.ZipJet.LaundriesOnMapContract;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.xbbxsnia.the2806.ZipJet.models.Laundry;

import java.util.ArrayList;
import java.util.List;

public class LaundriesOnMapViewModel extends AndroidViewModel{
    private List<Laundry> laundries = new ArrayList<>();

    public LaundriesOnMapViewModel(@NonNull Application application) {
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
            laundry.setAvatarUrl("https://picsum.photos/500/500?random=5");
            laundry.setAddress("تهران ، سعادت آباد میدان کاج...");
            laundries.add(laundry);
        }
        return laundries;
    }
}
