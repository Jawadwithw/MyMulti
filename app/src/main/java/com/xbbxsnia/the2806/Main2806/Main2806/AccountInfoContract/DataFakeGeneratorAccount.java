package com.xbbxsnia.the2806.Main2806.Main2806.AccountInfoContract;

import android.content.Context;

import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;

public class DataFakeGeneratorAccount {


    public static ArrayList<MenuAppData> getMoreDatas(Context context) {
        MenuAppData appData;
        ArrayList<MenuAppData> appDataList = new ArrayList<>();
        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.wallet));
        appData.setFeatureImage(R.drawable.ic_wallet);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.colleague));
        appData.setFeatureImage(R.drawable.ic_group_cooperate);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.colleague_orders));
        appData.setFeatureImage(R.drawable.ic_group_cooperate_orders);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.support));
        appData.setFeatureImage(R.drawable.ic_headphone);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.rules));
        appData.setFeatureImage(R.drawable.ic_paper);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.all_orders));
        appData.setFeatureImage(R.drawable.ic_paper);
        appDataList.add(appData);

        appData = new MenuAppData();
        appData.setTitle(context.getString(R.string.logout));
        appData.setFeatureImage(R.drawable.ic_logout);
        appDataList.add(appData);


        return appDataList;
    }
}
