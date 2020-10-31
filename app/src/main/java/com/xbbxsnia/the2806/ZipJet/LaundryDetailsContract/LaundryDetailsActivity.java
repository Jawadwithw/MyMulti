package com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.CreateOrderNotification;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.DirectoryContract.DirectoryActivity;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.AllContract.AllOrdersActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.Main2806.Main2806.StartContract.StartActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.LaundryCommentAdapter;
import com.xbbxsnia.the2806.ZipJet.Adapters.LaundrySliderAdapter;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnOrderConfirmed;
import com.xbbxsnia.the2806.ZipJet.models.Laundry;
import com.xbbxsnia.the2806.ZipJet.models.LaundryComment;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.IS_MY_ORDER;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.LOCATION_GEO;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_FOR;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_FROM;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_ID;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_STATUS;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.PENDING;

public class LaundryDetailsActivity extends BaseActivity implements BaseView {
    private ParseObject customer_detail;
    private LaundryDetailsViewModel viewModel;
    private ImageView ivBackArrow;
    private SliderView sliderView;
    private String code;
    private Laundry laundry;
    private ImageView ivLaundryAvatar, iv_add_cmm;
    private TextView tvLaundryName;
    private TextView tvLaundryPhoneNumber;
    private TextView tvLaundryAddress;
    private TextView tvRate;
    private TextView tv_title;
    private ImageView ivCallArrow;
    private ImageView ivMapArrow;
    private ExtendedFloatingActionButton fab_order;
    private String cooperate_username, cooperate_type, cooperate_title;
    private DialogGenerator dialogGenerator;
    private Dialog rating_dialog;
    public static final String CMM_NAME = "cmm_name";
    public static final String CMM_DATE = "cmm_date";
    public static final String CMM_RATE = "cmm_rate";
    public static final String CMM_BODY = "cmm_body";
    public static final String CMM_IS_CONFIRMED = "cmm_is_confirmed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_details);
        code = getIntent().getStringExtra("code");
        setupViews();
    }

    @Override
    public void setupViews() {
        dialogGenerator = new DialogGenerator();

        Intent intent = getIntent();
        viewModel = ViewModelProviders.of(this).get(LaundryDetailsViewModel.class);
        ivBackArrow = findViewById(R.id.iv_laundryDetailsActivity_backArrow);
        ivBackArrow.setOnClickListener(v -> onBackPressed());
        sliderView = findViewById(R.id.imageSlider_laundryDetailsActivity);
        fab_order = (ExtendedFloatingActionButton) findViewById(R.id.fab_zipJetHomeActivity_plus);


        ivLaundryAvatar = findViewById(R.id.iv_laundryDetailsActivity_laundryAvatar);
        iv_add_cmm = findViewById(R.id.iv_comment_add);
        tvLaundryName = findViewById(R.id.tv_laundryDetailsActivity_laundryName);
        tvLaundryPhoneNumber = findViewById(R.id.tv_laundryDetailsActivity_phoneNumber);
        tvLaundryAddress = findViewById(R.id.tv_laundryDetailsActivity_address);
        ivCallArrow = findViewById(R.id.iv_laundryDetailsActivity_callArrow);
        ivMapArrow = findViewById(R.id.iv_laundryDetailsActivity_mapArrow);
        tvRate = findViewById(R.id.tv_laundryDetailsActivity_rate);
        tv_title = findViewById(R.id.tv_title);


        iv_add_cmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating_dialog.show();
            }
        });


        setDetails();

        fab_order.setOnClickListener(view -> {
            sendDetailsToCustomer();
        });
    }

    private void sendDetailsToCustomer() {
        DialogGenerator dialogGenerator = new DialogGenerator();
        Dialog dialog = dialogGenerator.progressDialog(getViewContext());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("tbl_" + customer_detail.getString(ORDER_FROM));
        query.whereEqualTo("objectId", customer_detail.getString(ORDER_ID));
        dialog.show();
        query.getFirstInBackground((object, e) -> {
            if (e == null) {
                object.put(ORDER_STATUS, OrderActivity.ARRIVAL);
                object.put(ColleagueActivity.COOPERATE_JOB_TITLE, cooperate_title);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {


                        CreateOrderNotification orderNotification = new CreateOrderNotification();
                        //send notific to admin head captain
                        orderNotification.createOrderNotification(customer_detail.getString(ORDER_FROM), getResources().getString(R.string.order_status_changed));
                        saveForCaptain(cooperate_username, new OnOrderConfirmed() {
                            @Override
                            public void orderConfirmed(boolean confirmed) {
                                dialog.dismiss();
                                if (confirmed) {
                                    orderNotification.createOrderNotification(cooperate_username, getResources().getString(R.string.order_notific_message));
                                    AllOrdersActivity.all_orders.finish();
                                    DirectoryActivity.directoryActivity.finish();
                                    finish();
                                }
                            }
                        });

                    }
                });
                //send notfication
            }
        });

    }


    private void setDetails() {
        Intent intent = getIntent();
        cooperate_username = intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_USERNAME);
        cooperate_type = intent.getStringExtra(ColleagueActivity.COOPERATE_TYPE);
        cooperate_title = intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_TITLE);
        tvLaundryName.setText(cooperate_title);
        tv_title.setText(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_TITLE));
        tvLaundryPhoneNumber.setText(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_PHONE));
        tvLaundryAddress.setText(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_ADDRESS));
        tvRate.setText(String.valueOf(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_RATE)));
        Picasso.get().load(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_IMAGE_URL)).into(ivLaundryAvatar);
        ArrayList<String> images = new ArrayList<>();
        images.add(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_IMAGE_URL));
        sliderView.setSliderAdapter(new LaundrySliderAdapter(images, getViewContext()));
        rating_dialog = dialogGenerator.generateDialogRating(cooperate_username, getViewContext());
        RecyclerView rvLaundryComment = findViewById(R.id.rv_laundryDetailsActivity_comments);
        LaundryCommentAdapter laundryCommentAdapter = new LaundryCommentAdapter();
        rvLaundryComment.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        viewModel.getComments(getViewContext(), cooperate_username, new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                laundryCommentAdapter.setComments(getViewContext(), parseObjects);
                rvLaundryComment.setAdapter(laundryCommentAdapter);
            }
        });

        ivCallArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + Objects.requireNonNull(intent.getStringExtra(ColleagueActivity.COOPERATE_JOB_PHONE)).trim();
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse(uri));
                startActivity(intent1);
            }
        });
        switch (cooperate_type) {
            case ColleagueActivity.CAPTAIN_CAR:
                fab_order.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case ColleagueActivity.ZIP_JET:
                fab_order.setBackgroundColor(getResources().getColor(R.color.zipJetColorPrimary));

                break;
        }

        getCustomerDetails();
    }

    private void getCustomerDetails() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer_Order");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                customer_detail = (ParseObject) objects.get(0).get(ColleagueActivity.ORDER_KEY);
                //  Log.d("Gson parse", "setupViews: "+pars2.getString(OrderActivity.NAME));
                ParseObject.unpinAllInBackground(objects);
            }
        });
    }

    private void saveForCaptain(String tbl_name, OnOrderConfirmed onOrderConfirmed) {
        ParseObject parseObject = new ParseObject("tbl_" + tbl_name);
        parseObject.put((OrderActivity.PROBLEM_DESC), customer_detail.getString(OrderActivity.PROBLEM_DESC));
        parseObject.put((OrderActivity.NAME), customer_detail.getString(OrderActivity.NAME));
        parseObject.put((OrderActivity.ORDER_TIME), customer_detail.getString(OrderActivity.ORDER_TIME));
        parseObject.put((OrderActivity.ORDER_DATE), customer_detail.getString(OrderActivity.ORDER_DATE));
        parseObject.put(LOCATION_GEO, customer_detail.getParseGeoPoint(LOCATION_GEO));
        parseObject.put((OrderActivity.TOTAL_ADDRESS), customer_detail.getString(OrderActivity.TOTAL_ADDRESS));
        parseObject.put(ORDER_FOR, customer_detail.getString(ORDER_FOR));
        parseObject.put(ORDER_FROM, customer_detail.getString(ORDER_FROM));
        parseObject.put(IS_MY_ORDER, false);

        switch (cooperate_type) {
            case ColleagueActivity.CAPTAIN_CAR:
                parseObject.put((OrderActivity.LAST_NAME), customer_detail.getString(OrderActivity.LAST_NAME));
                parseObject.put((OrderActivity.CAR_BRAND), customer_detail.getString(OrderActivity.CAR_BRAND));
                parseObject.put((OrderActivity.CARD_MODEL), customer_detail.getString(OrderActivity.CARD_MODEL));
                parseObject.put((OrderActivity.PROBLEM_NAME), customer_detail.getString(OrderActivity.PROBLEM_NAME));
                parseObject.put((OrderActivity.PROBLEM_TYPE), customer_detail.getString(OrderActivity.PROBLEM_TYPE));
                break;
            case ColleagueActivity.ZIP_JET:
                parseObject.put((OrderActivity.ORDER_CLOTH_TYPE), customer_detail.getString(OrderActivity.ORDER_CLOTH_TYPE));
                parseObject.put((OrderActivity.ORDER_COLOR), customer_detail.getString(OrderActivity.ORDER_COLOR));
                break;
        }

        parseObject.saveInBackground(e -> onOrderConfirmed.orderConfirmed(true));

    }

    @Override
    public Context getViewContext() {
        return this;
    }
}