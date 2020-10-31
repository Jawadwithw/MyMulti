package com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.Main2806.Main2806.MapContract.MapActivity;
import com.xbbxsnia.the2806.R;

public class ColleagueActivity extends BaseActivity implements BaseView {
    public static final String COOPERATE_JOB_TITLE = "c_job_title";
    public static final String COOPERATE_JOB_PHONE = "c_job_phone";
    public static final String COOPERATE_JOB_ADDRESS = "c_job_address";
    public static final String COOPERATE_JOB_IMAGE_URL = "c_job_image_url";
    public static final String COOPERATE_JOB_DESC = "c_job_desc";
    public static final String COOPERATE_JOB_LOCATION = "c_job_location";
    public static final String COOPERATE_JOB_USERNAME = "c_job_username";
    public static final String COOPERATE_JOB_IS_CONFIRMED = "c_job_is_confirmed";
    public static final String COOPERATE_JOB_RATE = "c_job_rate";
    public static final String CAPTAIN_CAR = "cooperate_tbl_01";
    public static final String ZIP_JET = "cooperate_tbl_02";
    public static final String CAPTAIN_BIKE = "cooperate_tbl_03";
    public static final String CAPTAIN_FRUIT = "cooperate_tbl_04";
    public static final String COOPERATE_TYPE = "cooperate_type";
    public static final String TBL_CUSTOMERS = "Customer_Order";
    public static final String ORDER_KEY = "order_key";
    private String cooperate_tbl_name="";
    public static final int REQUEST_CODE = 1;
    private Spinner spinner_cooperate_type, spinner_cooperate_group;
    private ArrayAdapter<CharSequence> cooperate_group_adapter;
    private Button btn_map,btn_confirm;
    private EditText et_address,et_phone,et_job_desc,et_job_title;
    private double lat = 0;
    private double lang = 0;
    private DialogGenerator dialogGenerator;
    private Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colleague);
        setupViews();
    }

    @Override
    public void setupViews() {
        dialogGenerator = new DialogGenerator();
        progressDialog = dialogGenerator.progressDialog(getViewContext());
        btn_map = (Button) findViewById(R.id.btn_map);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        et_job_title=(EditText)findViewById(R.id.et_job_title);
        et_address=(EditText)findViewById(R.id.et_address);
        et_phone=(EditText)findViewById(R.id.et_phone_number);
        et_job_desc=(EditText)findViewById(R.id.et_job_desc);

        spinner_cooperate_type = (Spinner) findViewById(R.id.spinner_cooperate_type);
        spinner_cooperate_group = (Spinner) findViewById(R.id.spinner_cooperate_group);

        ArrayAdapter<CharSequence> problem_name_adapter = ArrayAdapter.createFromResource(getViewContext(), R.array.myMultiTypes, android.R.layout.simple_spinner_item);
        problem_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cooperate_type.setAdapter(problem_name_adapter);

        spinner_cooperate_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        cooperate_tbl_name = CAPTAIN_CAR;
                        break;
                    case 1:
                        cooperate_tbl_name = ZIP_JET;
                        break;
                    case 2:
                        cooperate_tbl_name = CAPTAIN_BIKE;

                        break;
                    case 3:
                        cooperate_tbl_name = CAPTAIN_FRUIT;
                        break;
                }

                if (i != 0) {
                    spinner_cooperate_group.setAdapter(null);
                } else {
                    setupCooperateGAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        setupCooperateGAdapter();

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getViewContext(),
                        MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                //procced to save the job!
                String job_title = et_job_title.getText().toString().trim();
                String job_phone = et_phone.getText().toString().trim();
                String job_address = et_address.getText().toString().trim();
                String job_desc = et_job_desc.getText().toString().trim();
                if (!job_title.isEmpty() && !job_phone.isEmpty() && !job_address.isEmpty() && !job_desc.isEmpty()){
                                    if (lat != 0 && lang !=0){
                                        //add to parse
                                        ParseObject parseObject = ParseObject.create(cooperate_tbl_name);
                                        parseObject.put(COOPERATE_JOB_TITLE,job_title);
                                        parseObject.put(COOPERATE_JOB_PHONE,job_phone);
                                        parseObject.put(COOPERATE_JOB_ADDRESS,job_address);
                                        parseObject.put(COOPERATE_JOB_DESC,job_desc);
                                        parseObject.put(COOPERATE_JOB_USERNAME, G.currentUser.getUsername());
                                        parseObject.put(COOPERATE_JOB_IS_CONFIRMED, false);
                                        parseObject.put(COOPERATE_JOB_RATE, "2.5");
                                        parseObject.put(COOPERATE_JOB_IMAGE_URL, "https://picsum.photos/500/500?random=1");
                                        ParseGeoPoint cooperate_location = new ParseGeoPoint(lat,lang);
                                        parseObject.put(COOPERATE_JOB_LOCATION,cooperate_location);
                                        progressDialog.show();
                                        parseObject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                progressDialog.dismiss();
                                                G.currentUser.put(cooperate_tbl_name,cooperate_tbl_name);
                                                G.currentUser.saveInBackground();
                                                if (e == null){

                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(getViewContext(),getResources().getString(R.string.select_from_map),Toast.LENGTH_SHORT).show();

                                    }
                                }else {
                                    Toast.makeText(getViewContext(),getResources().getString(R.string.fill_all_fileds),Toast.LENGTH_SHORT).show();
                                }

//                if (G.currentUser.get(cooperate_tbl_name) != cooperate_tbl_name){
//                    ParseQuery<ParseObject> query = ParseQuery.getQuery(cooperate_tbl_name);
//                    query.whereEqualTo(COOPERATE_JOB_USERNAME,G.currentUser.getUsername());
//                    query.getFirstInBackground(new GetCallback<ParseObject>() {
//                        @Override
//                        public void done(ParseObject object, ParseException e) {
//
//                            if (object == null){
//                                if (!job_title.isEmpty() && !job_phone.isEmpty() && !job_address.isEmpty() && !job_desc.isEmpty()){
//                                    if (lat != 0 && lang !=0){
//                                        //add to parse
//                                        ParseObject parseObject = ParseObject.create(cooperate_tbl_name);
//                                        parseObject.put(COOPERATE_JOB_TITLE,job_title);
//                                        parseObject.put(COOPERATE_JOB_PHONE,job_phone);
//                                        parseObject.put(COOPERATE_JOB_ADDRESS,job_address);
//                                        parseObject.put(COOPERATE_JOB_DESC,job_desc);
//                                        parseObject.put(COOPERATE_JOB_USERNAME, G.currentUser.getUsername());
//                                        parseObject.put(COOPERATE_JOB_IS_CONFIRMED, false);
//                                        parseObject.put(COOPERATE_JOB_RATE, "2.5");
//                                        parseObject.put(COOPERATE_JOB_IMAGE_URL, "https://picsum.photos/500/500?random=1");
//                                        ParseGeoPoint cooperate_location = new ParseGeoPoint(lat,lang);
//                                        parseObject.put(COOPERATE_JOB_LOCATION,cooperate_location);
//                                        progressDialog.show();
//                                        parseObject.saveInBackground(new SaveCallback() {
//                                            @Override
//                                            public void done(ParseException e) {
//                                                progressDialog.dismiss();
//                                                G.currentUser.put(cooperate_tbl_name,cooperate_tbl_name);
//                                                G.currentUser.saveInBackground();
//                                                if (e == null){
//
//                                                }
//                                            }
//                                        });
//
//                                    }else {
//                                        Toast.makeText(getViewContext(),getResources().getString(R.string.select_from_map),Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }else {
//                                    Toast.makeText(getViewContext(),getResources().getString(R.string.fill_all_fileds),Toast.LENGTH_SHORT).show();
//                                }
//                            }else {
//                                Toast.makeText(getViewContext(),getResources().getString(R.string.cooprate_already_set),Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//
//
//                }else {
//                    //not sigend up yet
//                    Toast.makeText(getViewContext(),getResources().getString(R.string.cooprate_already_set),Toast.LENGTH_SHORT).show();
//
//                }

            }
        });
    }

    private void setupCooperateGAdapter() {
        cooperate_group_adapter = ArrayAdapter.createFromResource(getViewContext(), R.array.problems, android.R.layout.simple_spinner_item);
        cooperate_group_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cooperate_group.setAdapter(cooperate_group_adapter);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

                lat = data.getDoubleExtra("lat", 0);
                lang = data.getDoubleExtra("long", 0);

            }
        } catch (Exception ex) {
            Toast.makeText(ColleagueActivity.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}