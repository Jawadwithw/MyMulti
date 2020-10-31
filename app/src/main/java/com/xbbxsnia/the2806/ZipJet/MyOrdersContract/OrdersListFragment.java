package com.xbbxsnia.the2806.ZipJet.MyOrdersContract;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.MyOrdersRvAdapter;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.util.List;

public class OrdersListFragment extends BaseFragment {
    private int pageType;
    private OrdersListViewModel viewModel;
    private List<Order> doneOrders;
    private List<Order> doingOrders;

    public OrdersListFragment() {

    }

    public OrdersListFragment(int pageType) {
        this.pageType = pageType;
    }

    public List<Order> getOrders () {
        if (pageType == MyOrdersActivity.DONE_ORDERS_PAGE)
            return doneOrders;
        else
            return doingOrders;
    }

    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(this).get(OrdersListViewModel.class);
        doneOrders = viewModel.getDoneOrders();
        doingOrders = viewModel.getDoingOrders();
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_ordersList_root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new MyOrdersRvAdapter(pageType, getOrders(), phoneNumber -> {
            String uri = "tel:" + phoneNumber.trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }));
    }

    @Override
    public int getLayout() {
        return R.layout.orders_list_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        OrdersListFragment newFragment = new OrdersListFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return false;
    }
}
