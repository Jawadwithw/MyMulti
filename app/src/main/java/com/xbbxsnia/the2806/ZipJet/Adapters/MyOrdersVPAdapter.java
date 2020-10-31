package com.xbbxsnia.the2806.ZipJet.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xbbxsnia.the2806.ZipJet.MyOrdersContract.MyOrdersActivity;
import com.xbbxsnia.the2806.ZipJet.MyOrdersContract.OrdersListFragment;
public class MyOrdersVPAdapter extends FragmentStateAdapter {

    public MyOrdersVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int pageType;
        switch (position){
            case 0:
                pageType = MyOrdersActivity.DONE_ORDERS_PAGE;
                break;
            case 1:
                pageType = MyOrdersActivity.DOING_ORDERS_PAGE;
                break;
            default:
                pageType = 100;
        }
        return new OrdersListFragment(pageType);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
