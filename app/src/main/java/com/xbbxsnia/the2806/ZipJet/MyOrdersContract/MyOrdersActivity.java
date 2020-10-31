package com.xbbxsnia.the2806.ZipJet.MyOrdersContract;

import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xbbxsnia.the2806.ZipJet.Adapters.MyOrdersVPAdapter;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.R;

public class MyOrdersActivity extends BaseActivity {
    public static final int DONE_ORDERS_PAGE = 0;
    public static final int DOING_ORDERS_PAGE = 1;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zipjet_my_orders);
        setupViews();
    }

    @Override
    public void setupViews() {
        ViewPager2 viewPager2 = findViewById(R.id.vp_myOrders);
        viewPager2.setAdapter(new MyOrdersVPAdapter(this));
        TabLayout tabLayout = findViewById(R.id.tl_myOrders);
        backArrow = findViewById(R.id.iv_myOrdersActivity_backArrow);
        backArrow.setOnClickListener(v -> onBackPressed());
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case DONE_ORDERS_PAGE:
                    tab.setText("انجام شده");
                    break;
                case DOING_ORDERS_PAGE:
                    tab.setText("در حال انجام");
                    break;
            }
        });
        mediator.attach();
    }
}