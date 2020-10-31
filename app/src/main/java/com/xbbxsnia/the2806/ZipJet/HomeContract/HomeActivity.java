package com.xbbxsnia.the2806.ZipJet.HomeContract;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.LaundryAdapter;
import com.xbbxsnia.the2806.ZipJet.LaundriesOnMapContract.LaundriesOnMapActivity;
import com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract.LaundryDetailsActivity;
import com.xbbxsnia.the2806.ZipJet.MyOrdersContract.MyOrdersActivity;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.ZipJet.models.User;

import java.text.NumberFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity {
    private CircleImageView ivUserAvatar;
    private TextView tvUserName;
    private TextView phoneNumber;
    private TextView tvUserBudget;
    private TextView tvUserAddress;
    private Button btnMyOrders;
    private TextView tvShowOnMap;
    private RecyclerView rvLaundriesDirectory;
    private ExtendedFloatingActionButton fabPlus;
    private HomeViewModel viewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupViews();
    }

    @Override
    public void setupViews() {
        ivUserAvatar = findViewById(R.id.iv_zipJetHomeActivity_userAvatar);
        tvUserName = findViewById(R.id.tv_zipJetHomeActivity_userName);
        tvUserBudget = findViewById(R.id.tv_zipJetHomeActivity_userBudget);
        tvUserAddress = findViewById(R.id.tv_zipJetHomeActivity_userAddress);
        btnMyOrders = findViewById(R.id.btn_zipJetHomeActivity_myOrders);
        phoneNumber = findViewById(R.id.tv_zipJetHomeActivity_phoneNumber);
        tvShowOnMap = findViewById(R.id.tv_zipJetHomeActivity_showOnMapButton);
        rvLaundriesDirectory = findViewById(R.id.rv_zipJetHomeActivity_laundriesDirectory);
        fabPlus = findViewById(R.id.fab_zipJetHomeActivity_plus);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        user = viewModel.getUser();

        Picasso.get().load(user.getUserAvatar()).into(ivUserAvatar);
        tvUserName.setText(user.getUserName());
        NumberFormat format = NumberFormat.getInstance();
        tvUserBudget.setText(format.format(user.getUserBudget()));
        tvUserAddress.setText(user.getUserAddress());
        phoneNumber.setText(user.getUserPhoneNumber());

        fabPlus.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        });

        btnMyOrders.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MyOrdersActivity.class)));

       // initRecyclerView();

        tvShowOnMap.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this , LaundriesOnMapActivity.class));
        });
    }

    public void initRecyclerView() {
//        rvLaundriesDirectory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
//        rvLaundriesDirectory.setAdapter(new LaundryAdapter(viewModel.getLaundries(), code -> {
//            Intent intent = new Intent(HomeActivity.this, LaundryDetailsActivity.class);
//            intent.putExtra("code", code);
//            startActivity(intent);
//        }));
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(rvLaundriesDirectory);
    }
}