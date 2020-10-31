package com.xbbxsnia.the2806.CapitanCar.MyOrdersContract;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends BaseActivity implements BaseView {
    MyOrdersViewModel myOrdersViewModel;
    MyOrdersAdapter myOrdersAdapter;
    RecyclerView rv_myOrders;
    private int page = 0;
    private int limit = 5;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    List<ParseObject> orderObjects= new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        setupViews();

        myOrdersViewModel = ViewModelProviders.of(this).get(MyOrdersViewModel.class);
        myOrdersViewModel.getOrders(getViewContext(), G.currentUser.getUsername(),page,limit, new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                Log.d("TAG", "myOrders: "+parseObjects.size());
                orderObjects.addAll(parseObjects);
                myOrdersAdapter.setRequests(getViewContext(),orderObjects);
                rv_myOrders.setAdapter(myOrdersAdapter);
            }
        });

    }

    @Override
    public void setupViews() {
        rv_myOrders=(RecyclerView)findViewById(R.id.rv_request);
       linearLayoutManager = new LinearLayoutManager(getViewContext());
        rv_myOrders.setLayoutManager(linearLayoutManager);
        myOrdersAdapter=new MyOrdersAdapter();


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

                            myOrdersViewModel.getOrders(getViewContext(), G.currentUser.getUsername(), page, limit, new OnParseObjectReceived() {
                                @Override
                                public void onReceived(List<ParseObject> parseObjects) {
                                    Log.v("...", "Loading other 5 objects..");

                                    if (parseObjects.size() > 4){
                                        loading = true;
                                    }
                                    orderObjects.addAll(parseObjects);
                                    myOrdersAdapter.setRequests(getViewContext(),orderObjects);
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
}