package com.xbbxsnia.the2806.Main2806.Main2806.MainContract;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xbbxsnia.the2806.Main2806.Main2806.HomeContract.HomeFragment;
import com.xbbxsnia.the2806.Main2806.Main2806.OrdersContract.OrdersFragment;
import com.xbbxsnia.the2806.Main2806.Main2806.AccountInfoContract.AccountInfoFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new OrdersFragment().newInstance();
            case 1:
                return new HomeFragment().newInstance();
            case 2:
                return new AccountInfoFragment().newInstance();
            default:
                return null;


        }
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "سفارش های من";
            case 1:
                return "ثبت سفارش";
            case 2:
                return "حساب کاربری";
            default:
                return "";

        }
    }

}
