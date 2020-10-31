package com.xbbxsnia.the2806.CapitanCar.OrderContract;

import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.parse.fcm.ParseFCM;
import com.xbbxsnia.the2806.CapitanCar.Adapters.OrderViewPagerAdapter;
import com.xbbxsnia.the2806.CapitanCar.AdressContract.AddressFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.CreateOrderNotification;
import com.xbbxsnia.the2806.CapitanCar.Data.OrdersViewPager;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.DateInfoContract.DateInfoFragment;
import com.xbbxsnia.the2806.CapitanCar.FinishContract.FinishFragment;
import com.xbbxsnia.the2806.CapitanCar.PersonalInfoContract.PersonalInfoFragment;
import com.xbbxsnia.the2806.CapitanCar.ProblemInfoContract.ProblemInfoFragment;
import com.xbbxsnia.the2806.CapitanCar.SingupContract.SignUpFragment;
import com.xbbxsnia.the2806.CapitanCar.VerificationContract.VerificationFragment;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.R;

import java.util.HashMap;
import java.util.Objects;


public class OrderActivity extends BaseActivity implements BaseView {
    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String CAR_BRAND = "car_brand";
    public static final String CARD_MODEL = "car_model";
    public static final String PROBLEM_NAME = "problem_name";
    public static final String PROBLEM_TYPE = "problem_type";
    public static final String PROBLEM_DESC = "problem_desc";
    public static final String ORDER_DATE = "order_date";
    public static final String ORDER_TIME = "order_time";
    public static final String ADDRESS_LAT = "address_lat";
    public static final String ADDRESS_LANG = "address_lang";
    public static final String ADDRESS = "address";
    public static final String TOTAL_ADDRESS = "total_address";
    public static final String VERIFICATION_CODE = "verification_code";
    public static final String GENDER_ID = "gender_id";
    public static final String GENDER = "gender";
    public static final String LOCATION_GEO = "location_geo";
    public static final String ORDER_FROM = "order_from";
    public static final String IS_MY_ORDER = "is_my_order";
    public static final String ORDER_FOR = "order_for";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_ID = "order_id";
    public static final String PENDING = "pending";
    public static final String ARRIVAL = "arrival";
    public static final String DONE = "done";
    //zipjet stuff
    public static final String ORDER_COLOR = "order_color";
    public static final String ORDER_CLOTH_TYPE = "cloth_type";

    private DialogGenerator dialogGenerator;
    private Dialog progressDialog;
    public static int currentItem = 0;
    private static final int MAX_STEPS = 6;
    Button btn_next, btn_previous, btn_watch_order;
    private ProgressBar progressBar;
    private OrderViewModel orderViewModel;
    private OrdersViewPager viewPager;
    private LinearLayout layout_btns_order;
    private ImageView iv_back;
    private String order_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_contract);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        setupViews();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentItem = 0;
    }

    @Override
    public void setupViews() {
        dialogGenerator = new DialogGenerator();
        progressDialog = dialogGenerator.progressDialog(getViewContext());
        iv_back = (ImageView) findViewById(R.id.iv_back);
        layout_btns_order = (LinearLayout) findViewById(R.id.btns_linear_layout);
        btn_watch_order = (Button) findViewById(R.id.btn_watch_order);
        viewPager = (OrdersViewPager) findViewById(R.id.view_pager);
        final OrderViewPagerAdapter adapter = new OrderViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_previous = (Button) findViewById(R.id.btn_previous);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(MAX_STEPS);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItem = 0;
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentItem < MAX_STEPS - 1) {

                    switch (currentItem) {
                        case 0:
                            PersonalInfoFragment personalInfoFragment = (PersonalInfoFragment) adapter.getFragment(currentItem);
                            if (personalInfoFragment.isConfirmed(getViewContext())) {
                                moveForward(currentItem);
                            }
                            break;
                        case 1:
                            ProblemInfoFragment problemInfoFragment = (ProblemInfoFragment) adapter.getFragment(currentItem);
                            if (problemInfoFragment.isConfirmed(getViewContext())) {
                                moveForward(currentItem);
                            }
                            break;
                        case 2:
                            DateInfoFragment dateInfoFragment = (DateInfoFragment) adapter.getFragment(currentItem);
                            if (dateInfoFragment.isConfirmed(getViewContext())) {
                                moveForward(currentItem);
                            }
                            break;
                        case 3:
                            AddressFragment addressFragment = (AddressFragment) adapter.getFragment(currentItem);
                            if (addressFragment.isConfirmed(getViewContext())) {
                                moveForward(currentItem);
                            }
                            break;
                        case 4:
                            SignUpFragment signUpFragment = (SignUpFragment) adapter.getFragment(currentItem);
                            Log.d("heres", "onClick: " + signUpFragment.isConfirmed(getViewContext()));
                            if (signUpFragment.isConfirmed(getViewContext())) {
                                //it comes true when current user is not null
                                moveForward(5);
                            }
                            break;
                        case 5:
                            VerificationFragment verificationFragment = (VerificationFragment) adapter.getFragment(currentItem);

                            break;
                        case 6:
                            FinishFragment finishFragment = (FinishFragment) adapter.getFragment(currentItem);
                            //   verificationFragment.isConfirmed(getViewContext());
                            break;
                    }


                }

            }
        });

        btn_watch_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.currentUser != null) {
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("device_id", G.currentUser.getUsername());
                    installation.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            saveData();
                            currentItem = 0;
                        }
                    });
                }


            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentItem > 0) {
                    currentItem--;
                    viewPager.setCurrentItem(currentItem);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBar.setProgress(currentItem, true);
                    } else {
                        progressBar.setProgress(currentItem);
                    }
                } else {
                    currentItem = 0;
                    finish();
                }


            }
        });

    }

    public void moveForward(int currentView) {
        currentView++;
        currentItem = currentView;
        viewPager.setCurrentItem(currentView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(currentView, true);
        } else {
            progressBar.setProgress(currentView);
        }

        if (currentItem > 4) {
            layout_btns_order.setVisibility(View.GONE);
            if (currentItem == 6) {
                btn_watch_order.setVisibility(View.VISIBLE);
            }
            iv_back.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }


    public UserSharedPrefManager sharedPrefManager() {
        UserSharedPrefManager sharedPrefManager = new UserSharedPrefManager(getViewContext());
        return sharedPrefManager;

    }

    public void saveData() {
        saveObject();
        // saveObject("order_" + ColleagueActivity.CAPTAIN_CAR, false);


    }

    private void saveObject() {
        ParseObject orderObject = OrderDetails("tbl_" + G.currentUser.getUsername(), true);
        progressDialog.show();
        orderObject.saveInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {

                order_id = orderObject.getObjectId();
                OrderDetails("order_" + ColleagueActivity.CAPTAIN_CAR, false).saveInBackground(e1 -> {
                    if (e1 == null) {
                        finish();
                        CreateOrderNotification orderNotification = new CreateOrderNotification();
                        //send notific to admin head captain
                        orderNotification.createOrderNotification("09120396076", getResources().getString(R.string.order_notific_message));
                    }
                });


            } else {
                Toast.makeText(getViewContext(), "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private ParseObject OrderDetails(String tbl_name, boolean isMine) {
        UserSharedPrefManager prefManager = this.sharedPrefManager();
        //here specefic ..
        ParseObject parseObject = ParseObject.create(tbl_name);

        //get user's location for parse
        ParseGeoPoint userGeoPoint = new ParseGeoPoint(Double.parseDouble(prefManager.getUserData(OrderActivity.ADDRESS_LAT)),
                Double.parseDouble(prefManager.getUserData(OrderActivity.ADDRESS_LANG)));
        G.currentUser.put(LOCATION_GEO, userGeoPoint);
        G.currentUser.saveInBackground();
        //
        parseObject.put((OrderActivity.NAME), prefManager.getUserData(OrderActivity.NAME));
        parseObject.put((OrderActivity.LAST_NAME), prefManager.getUserData(OrderActivity.LAST_NAME));
        parseObject.put((OrderActivity.CAR_BRAND), prefManager.getUserData(OrderActivity.CAR_BRAND));
        parseObject.put((OrderActivity.CARD_MODEL), prefManager.getUserData(OrderActivity.CARD_MODEL));
        parseObject.put((OrderActivity.PROBLEM_NAME), prefManager.getUserData(OrderActivity.PROBLEM_NAME));
        parseObject.put((OrderActivity.PROBLEM_TYPE), prefManager.getUserData(OrderActivity.PROBLEM_TYPE));
        parseObject.put((OrderActivity.PROBLEM_DESC), prefManager.getUserData(OrderActivity.PROBLEM_DESC));
        parseObject.put((OrderActivity.ORDER_TIME), prefManager.getUserData(OrderActivity.ORDER_TIME));
        parseObject.put((OrderActivity.ORDER_DATE), prefManager.getUserData(OrderActivity.ORDER_DATE));
        parseObject.put(LOCATION_GEO, userGeoPoint);
        parseObject.put(ORDER_FOR, ColleagueActivity.CAPTAIN_CAR);
        parseObject.put((OrderActivity.TOTAL_ADDRESS), prefManager.getUserData(OrderActivity.TOTAL_ADDRESS));
        parseObject.put(ORDER_FROM, G.currentUser.getUsername());
        if (isMine) {
            parseObject.put(IS_MY_ORDER, true);

            parseObject.put(ORDER_STATUS, PENDING);

        } else {
            parseObject.put(ORDER_ID, order_id);

        }
        return parseObject;
    }

}