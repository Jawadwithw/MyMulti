package com.xbbxsnia.the2806.ZipJet.OrderContract.ClotheTypeContract;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.ClothNumberModel;

import java.util.ArrayList;
import java.util.List;

public class ClotheTypeViewModel extends AndroidViewModel {
    private List<ClothNumberModel> clothes;

    public ClotheTypeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<ClothNumberModel> getClothes() {
        clothes = new ArrayList<>();

        ClothNumberModel shirt = new ClothNumberModel();
        shirt.setClothType("shirt");
        shirt.setImageUrl(String.valueOf(R.drawable.ic_shirt_blue));
        shirt.setPrice(10000);
        clothes.add(shirt);

        ClothNumberModel pants = new ClothNumberModel();
        pants.setClothType("pant");
        pants.setImageUrl(String.valueOf(R.drawable.ic_trousers_blue));
        pants.setPrice(5000);
        clothes.add(pants);

        ClothNumberModel hoodie = new ClothNumberModel();
        hoodie.setClothType("hoodie");
        hoodie.setImageUrl(String.valueOf(R.drawable.ic_hoodie_yellow));
        hoodie.setPrice(7000);
        clothes.add(hoodie);

        ClothNumberModel shirts = new ClothNumberModel();
        shirts.setClothType("shirts");
        shirts.setImageUrl(String.valueOf(R.drawable.ic_shirts_blue));
        shirts.setPrice(15000);
        clothes.add(shirts);

        ClothNumberModel dress = new ClothNumberModel();
        dress.setClothType("dress");
        dress.setImageUrl(String.valueOf(R.drawable.ic_dress_yellow));
        dress.setPrice(13000);
        clothes.add(dress);

        return clothes;
    }
}
