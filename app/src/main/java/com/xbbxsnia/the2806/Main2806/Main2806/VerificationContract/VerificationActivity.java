package com.xbbxsnia.the2806.Main2806.Main2806.VerificationContract;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;

import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.MainContract.MainActivity;
import com.xbbxsnia.the2806.R;


public class VerificationActivity extends BaseActivity implements BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifictaion);
        setupViews();
    }

    @Override
    public void setupViews() {
        final String random_pass= getIntent().getStringExtra("random_pass");
        final PinEntryEditText entryEditText=(PinEntryEditText)findViewById(R.id.txt_pin_entry);
        entryEditText.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {

                if (str.toString().equals(random_pass)){
                    //procced to finish fragment
                    G.currentUser= ParseUser.getCurrentUser();
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("device_id", G.currentUser.getUsername());
                    installation.saveInBackground();
                    startActivity(new Intent(VerificationActivity.this, MainActivity.class));
                    finish();
                }else {
                    entryEditText.setText(null);
                    Toast.makeText(getViewContext(), "کد فعالسازی و عدد وارد شده یکسان  نمی باشند..", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}