package com.xbbxsnia.the2806.Main2806.Main2806.Data;

import com.xbbxsnia.the2806.R;

import java.util.ArrayList;


public class DataFakeGeneratorTab {
    public static ArrayList<MenuAppData> getMoreDatas(){
        MenuAppData appData;
        ArrayList<MenuAppData> appDataList = new ArrayList<>();
        appData = new MenuAppData();
        appData.setTitle("");
        appData.setFeatureImage(R.drawable.ic_frame);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle("");
        appData.setFeatureImage(R.drawable.ic_home);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle("");
        appData.setFeatureImage(R.drawable.ic_account_info);
        appDataList.add(appData);

        return appDataList;
    }
}
