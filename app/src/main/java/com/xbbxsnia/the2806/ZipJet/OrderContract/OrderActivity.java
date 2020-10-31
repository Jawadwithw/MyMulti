package com.xbbxsnia.the2806.ZipJet.OrderContract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.OrdersVPAdapter;
import com.xbbxsnia.the2806.ZipJet.models.Order;

public class OrderActivity extends BaseActivity implements OnNextOrBackClickedCallBack, BaseView {
    private ViewPager2 viewPager;
    private OrderViewModel viewModel;
    private ImageView ivBackArrow;
    private OrdersVPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setupViews();
    }

    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        viewPager = findViewById(R.id.vp_orderActivity);
        adapter = new OrdersVPAdapter(this, this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        ivBackArrow = findViewById(R.id.iv_orderActivity_backArrow);
        ivBackArrow.setOnClickListener(v -> onBackPressed());

        Intent intent=getIntent();
        UserSharedPrefManager sharedPrefManager = new UserSharedPrefManager(getViewContext());
        sharedPrefManager.saveUserData(ColleagueActivity.COOPERATE_JOB_USERNAME,intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_USERNAME));

    }

    @Override
    public void onNextClick(int position, Order order) {
        adapter.setOrder(order);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onBackClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        switch (viewPager.getCurrentItem()){
            case 0:
                super.onBackPressed();
                break;
            case 1:
                viewPager.setCurrentItem(0);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}