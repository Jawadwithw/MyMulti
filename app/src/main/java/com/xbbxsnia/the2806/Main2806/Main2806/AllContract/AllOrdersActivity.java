package com.xbbxsnia.the2806.Main2806.Main2806.AllContract;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.OrdersContract.MyOrdersAdapter;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends BaseActivity implements BaseView {
    private RadioButton checkedRadioButton;
    AllOrdersViewModel allOrdersViewModel;
    AllOrdersAdapter myOrdersAdapter;
    RecyclerView rv_myOrders;
    private int page = 0;
    private int limit = 5;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    List<ParseObject> orderObjects = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private String app_name = ColleagueActivity.CAPTAIN_CAR;
    private LinearLayout filter_linear;
    private ImageView iv_filter, iv_close;
    private TextView tv_title;
    public static BaseActivity all_orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        all_orders=this;
        setupViews();
    }

    @Override
    public void setupViews() {
        filter_linear = findViewById(R.id.filter_view);
        iv_filter = findViewById(R.id.iv_filter);
        iv_close = findViewById(R.id.iv_close_filter);
        tv_title = findViewById(R.id.title_directories);


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
              hideFilter();
            }
        });

        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rg_apps);
        //   checkedRadioButton = (RadioButton) rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                String checkedName = checkedRadioButton.getText().toString();
                if (checkedName.equals(getResources().getString(R.string.captan_car))) {
                    app_name = ColleagueActivity.CAPTAIN_CAR;
                    tv_title.setText(getResources().getString(R.string.captan_car));
                } else if (checkedName.equals(getResources().getString(R.string.zip_jet))) {
                    app_name = ColleagueActivity.ZIP_JET;
                    tv_title.setText(getResources().getString(R.string.zip_jet));



                } else if (checkedName.equals(getResources().getString(R.string.capitan_bike))) {

                    app_name = ColleagueActivity.CAPTAIN_BIKE;
                    tv_title.setText(getResources().getString(R.string.capitan_bike));


                } else if (checkedName.equals(getResources().getString(R.string.capitan_fruit))) {
                    app_name = ColleagueActivity.CAPTAIN_FRUIT;
                    tv_title.setText(getResources().getString(R.string.capitan_fruit));


                }

                page = 0;
                limit = 5;
                loading = true;
                orderObjects.clear();
                getOrder(getViewContext(),app_name,page,limit);
                hideFilter();
            }
        });
        rv_myOrders = (RecyclerView) findViewById(R.id.rv_all);
        linearLayoutManager = new LinearLayoutManager(getViewContext());
        rv_myOrders.setLayoutManager(linearLayoutManager);
        myOrdersAdapter = new AllOrdersAdapter();


        allOrdersViewModel = ViewModelProviders.of(this).get(AllOrdersViewModel.class);
        getOrder(getViewContext(), app_name, page, limit);

        rv_myOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            page++;

                            allOrdersViewModel.getOrders(getViewContext(), app_name, page, limit, new OnParseObjectReceived() {
                                @Override
                                public void onReceived(List<ParseObject> parseObjects) {
                                    Log.v("...", "Loading other 5 objects..");

                                    if (parseObjects.size() > 4) {
                                        loading = true;
                                    }
                                    orderObjects.addAll(parseObjects);
                                    myOrdersAdapter.setRequests(getViewContext(),app_name, orderObjects);
                                    rv_myOrders.setAdapter(myOrdersAdapter);

                                }
                            });
                        }
                    }
                }
            }
        });


    }

    @Override
    public Context getViewContext() {
        return this;
    }

    private void getOrder(Context context, String app_name, int page, int limit) {
        allOrdersViewModel.getOrders(context, app_name, page, limit, new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                orderObjects.addAll(parseObjects);
                myOrdersAdapter.setRequests(getViewContext(),app_name, orderObjects);
                rv_myOrders.setAdapter(myOrdersAdapter);
            }
        });
    }

    private void hideFilter(){
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
}