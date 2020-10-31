package com.xbbxsnia.the2806.ZipJet.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xbbxsnia.the2806.ZipJet.OrderContract.ChooseOrderTimeContract.ChooseOrderTimeFragment;
import com.xbbxsnia.the2806.ZipJet.OrderContract.ClotheTypeContract.ClotheTypeFragment;
import com.xbbxsnia.the2806.ZipJet.OrderContract.Invoice.InvoiceFragment;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnNextOrBackClickedCallBack;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.util.List;

public class OrdersVPAdapter extends FragmentStateAdapter {
    private OnNextOrBackClickedCallBack callBack;
    private Order order;

    public OrdersVPAdapter(@NonNull FragmentActivity fragmentActivity, OnNextOrBackClickedCallBack callBack) {
        super(fragmentActivity);
        this.callBack = callBack;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ClotheTypeFragment(callBack);
            case 1:
                return new ChooseOrderTimeFragment(callBack, order);
            case 2:
                return new InvoiceFragment(order);
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
