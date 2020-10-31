package com.xbbxsnia.the2806.Main2806.Main2806.ProfileContract;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.OnUserDataFetched;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.SignUp;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.UserAppData;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.isSignupOk;
import com.xbbxsnia.the2806.Main2806.Main2806.VerificationContract.VerificationActivity;
import com.xbbxsnia.the2806.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class ProfileActivity extends BaseActivity implements BaseView {
    private RadioButton checkedRadioButton;
    private EditText et_name, et_last_name, et_phone;
    private AutoCompleteTextView et_address;
    private CircularProgressButton btn_confirm;
    private RadioGroup rGroup;
    private ProfileViewModel profileViewModel;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

            profileViewModel.getProfileData(getViewContext(), new OnUserDataFetched() {
                @Override
                public void userDataFetched(UserAppData userAppData) {
                    et_name.setText(userAppData.getName());
                    et_last_name.setText(userAppData.getLast_name());
                    et_address.setText(userAppData.getAddress());
                    if (G.currentUser != null){
                        et_phone.setText(userAppData.getUser_name());
                        et_phone.setEnabled(false);

                    }
                    if (!userAppData.getGender_id().equals("")){
                        rGroup.check(Integer.parseInt(userAppData.getGender_id()));

                    }
                }
            });


    }

    @Override
    public void setupViews() {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        btn_confirm = (CircularProgressButton) findViewById(R.id.btn_confirm);
        checkedRadioButton = (RadioButton) rGroup.findViewById(rGroup.getCheckedRadioButtonId());

        et_name = (EditText) findViewById(R.id.et_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_phone = (EditText) findViewById(R.id.et_phone_number);
        et_address = (AutoCompleteTextView) findViewById(R.id.et_address);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                boolean isChecked = checkedRadioButton.isChecked();
                Log.d("mytag", "onCheckedChanged: " + checkedRadioButton.getText());
                Log.d("mytag", "onCheckedChanged: " + i);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedRadioButton = (RadioButton) rGroup.findViewById(rGroup.getCheckedRadioButtonId());
                String name = et_name.getText().toString();
                String last_name = et_last_name.getText().toString();
                String address = et_address.getText().toString();
                String phone_number = et_phone.getText().toString();
                String gender_id = String.valueOf(rGroup.getCheckedRadioButtonId());
                String gender = String.valueOf(checkedRadioButton.getText());

                if (!name.isEmpty() && !last_name.isEmpty() && !address.isEmpty() && !phone_number.isEmpty()) {
                    UserSharedPrefManager sharedPrefManager = new UserSharedPrefManager(getViewContext());
                    sharedPrefManager.saveUserData(OrderActivity.NAME, name);
                    sharedPrefManager.saveUserData(OrderActivity.LAST_NAME, last_name);
                    sharedPrefManager.saveUserData(OrderActivity.ADDRESS, address);
                    sharedPrefManager.saveUserData(OrderActivity.GENDER_ID, gender_id);
                    sharedPrefManager.saveUserData(OrderActivity.GENDER, gender);
                    if (G.currentUser == null) {
                        SignUp signUp = new SignUp(getViewContext(), et_phone, btn_confirm, getApplication());
                        signUp.signUpUser(new isSignupOk() {
                            @Override
                            public void signUpOk(boolean isOk, int random_pass) {
                                if (isOk) {
                                    Intent intent = new Intent(ProfileActivity.this, VerificationActivity.class);
                                    intent.putExtra("random_pass", String.valueOf(random_pass));
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getViewContext(), getResources().getString(R.string.profile_toast), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(getViewContext(), getResources().getString(R.string.fill_all_fileds), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}