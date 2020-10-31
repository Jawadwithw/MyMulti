package com.xbbxsnia.the2806.Main2806.Main2806.StartContract;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.SignUp;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.isSignupOk;
import com.xbbxsnia.the2806.Main2806.Main2806.MainContract.MainActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.VerificationContract.VerificationActivity;
import com.xbbxsnia.the2806.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class StartActivity extends BaseActivity implements BaseView {
    CircularProgressButton btn_confirm;
    EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setupViews();
    }

    @Override
    public void setupViews() {
        btn_confirm = (CircularProgressButton)findViewById(R.id.btn_sign_up);
        et_phone=(EditText)findViewById(R.id.et_phone_number);

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    btn_confirm.setText(getResources().getString(R.string.bt_confirm_phone));
                }else {
                    btn_confirm.setText(getResources().getString(R.string.guest));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_phone.getText().toString().isEmpty()){
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                    //intent to main acitivty
                }else {
                    //proceed to sign up
                    SignUp signUp = new SignUp(getViewContext(),et_phone,btn_confirm,getApplication());
                    signUp.signUpUser(new isSignupOk() {
                        @Override
                        public void signUpOk(boolean isOk, int random_pass) {
                            if (isOk){
                                Intent intent = new Intent(StartActivity.this, VerificationActivity.class);
                                intent.putExtra("random_pass",String.valueOf(random_pass));
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}