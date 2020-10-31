package com.xbbxsnia.the2806.Main2806.Main2806.HomeContract;

import android.content.Context;

import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;

public class DataGeneratorHome {
    public static ArrayList<MenuAppData> getDatas(Context context) {

        MenuAppData appData;
        ArrayList<MenuAppData> appDataList = new ArrayList<>();
        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.fanni));
        appData.setFeatureImage(R.drawable.ic_fanni);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.lux));
        appData.setFeatureImage(R.drawable.ic_lux);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.emdad));
        appData.setFeatureImage(R.drawable.ic_emdad);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.ironing));
        appData.setFeatureImage(R.drawable.ic_otu);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.stain_removal));
        appData.setFeatureImage(R.drawable.ic_lakke);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.washing));
        appData.setFeatureImage(R.drawable.ic_tshirt);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.steam_washing));
        appData.setFeatureImage(R.drawable.ic_bokhar);
        appDataList.add(appData);

        return appDataList;


    }
}
