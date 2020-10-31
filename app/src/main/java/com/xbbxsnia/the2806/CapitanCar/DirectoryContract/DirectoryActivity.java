package com.xbbxsnia.the2806.CapitanCar.DirectoryContract;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.AllContract.AllOrdersActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.util.List;
import java.util.Map;

public class DirectoryActivity extends BaseActivity implements BaseView {
    private RecyclerView rv_directory;
    private LinearLayoutManager linearLayoutManager;
    private DirectoryAdapter directoryAdapter;
    private DirectoryViewModel directoryViewModel;
    private LinearLayout filter_linear;
    private ImageView iv_filter, iv_close;
    private TextView tv_title;
    public static BaseActivity directoryActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        directoryActivity=this;
        setupViews();
    }

    @Override
    public void setupViews() {
        Intent intent = getIntent();

        String directory_type = intent.getStringExtra(ColleagueActivity.COOPERATE_TYPE);
        filter_linear = findViewById(R.id.filter_view);
        iv_filter = findViewById(R.id.iv_filter);
        iv_close = findViewById(R.id.iv_close_filter);
        tv_title = findViewById(R.id.title_directories);
        switch (directory_type) {
            case ColleagueActivity.CAPTAIN_CAR:
                tv_title.setText(getResources().getString(R.string.captan_car_directorie));
                break;
            case ColleagueActivity.ZIP_JET:
                tv_title.setText(getResources().getString(R.string.laundries_directory));

                break;
        }
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Prepare the View for the animation
                filter_linear.setVisibility(View.VISIBLE);
                filter_linear.setAlpha(0.0f);

// Start the animation
                filter_linear.animate()
                        .translationY(10)
                        .alpha(1.0f)
                        .setListener(null);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_linear.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                filter_linear.setVisibility(View.GONE);
                            }
                        });
            }
        });

        rv_directory = (RecyclerView) findViewById(R.id.rv_directory);
        linearLayoutManager = new LinearLayoutManager(getViewContext());
        rv_directory.setLayoutManager(linearLayoutManager);
        directoryAdapter = new DirectoryAdapter();
        directoryViewModel = ViewModelProviders.of(this).get(DirectoryViewModel.class);
        directoryAdapter.startShimmer();
        rv_directory.setAdapter(directoryAdapter);
        directoryViewModel.getDirectories(getViewContext(), directory_type, new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                directoryAdapter.setDirectories(getViewContext(), directory_type, parseObjects);
                rv_directory.setAdapter(directoryAdapter);
            }
        });

    }

    @Override
    public Context getViewContext() {
        return this;
    }


}