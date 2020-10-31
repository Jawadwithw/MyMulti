package com.xbbxsnia.the2806.Main2806.Main2806.ColleagueOrders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseObject;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.AllContract.AllOrdersAdapter;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;
import java.util.List;

public class ColleagueOrdersActivity extends BaseActivity implements BaseView {
    private RecyclerView rv_colleagueOrders;
    private LinearLayoutManager linearLayoutManager;
    private ColleagueOrdersViewModel ordersViewModel;
    private ColleagueOrdersAdapter allOrdersAdapter;
    private int page = 0;
    private int limit = 5;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    List<ParseObject> orderObjects = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colleague_orders);
        setupViews();
    }

    @Override
    public void setupViews() {
        rv_colleagueOrders = (RecyclerView) findViewById(R.id.rv_all);
        linearLayoutManager = new LinearLayoutManager(getViewContext());
        rv_colleagueOrders.setLayoutManager(linearLayoutManager);
        allOrdersAdapter = new ColleagueOrdersAdapter();

        ordersViewModel = ViewModelProviders.of(this).get(ColleagueOrdersViewModel.class);
        ordersViewModel.getOrders(getViewContext(), G.currentUser.getUsername(), page, limit, new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                orderObjects.addAll(parseObjects);
                allOrdersAdapter.setOrders(getViewContext(), orderObjects);
                rv_colleagueOrders.setAdapter(allOrdersAdapter);
            }
        });

        rv_colleagueOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                            ordersViewModel.getOrders(getViewContext(), G.currentUser.getUsername(), page, limit, new OnParseObjectReceived() {
                                @Override
                                public void onReceived(List<ParseObject> parseObjects) {
                                    Log.v("...", "Loading other 5 objects..");

                                    if (parseObjects.size() > 4) {
                                        loading = true;
                                    }
                                    orderObjects.addAll(parseObjects);
                                    allOrdersAdapter.setOrders(getViewContext(), orderObjects);
                                    rv_colleagueOrders.setAdapter(allOrdersAdapter);

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