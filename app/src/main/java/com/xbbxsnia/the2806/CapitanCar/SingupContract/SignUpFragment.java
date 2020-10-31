package com.xbbxsnia.the2806.CapitanCar.SingupContract;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.InstallPush;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseExceptionReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.Repository;
import com.xbbxsnia.the2806.CapitanCar.Data.SMS;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.SignUp;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.isSignupOk;
import com.xbbxsnia.the2806.R;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SignUpFragment extends BaseFragment implements BaseView {
    private EditText et_phone_number;
    private CircularProgressButton btn_sign_up;
    private TextView tv_log_out, tv_logout_desc;
    private String phone_number;

    @Override
    public void setupViews() {
        et_phone_number = (EditText) rootView.findViewById(R.id.et_phone_number);
        btn_sign_up = (CircularProgressButton) rootView.findViewById(R.id.btn_sign_up);
        tv_log_out = (TextView) rootView.findViewById(R.id.tv_sign_out);
        tv_logout_desc = (TextView) rootView.findViewById(R.id.tv_logout_desc);


        if (G.currentUser != null) {
            et_phone_number.setText(G.currentUser.getUsername());
            et_phone_number.setEnabled(false);
            tv_log_out.setVisibility(View.VISIBLE);
            tv_logout_desc.setVisibility(View.VISIBLE);
            btn_sign_up.setVisibility(View.GONE);
        } else {
            et_phone_number.setText("");
            et_phone_number.setEnabled(true);
        }


        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (G.currentUser == null) {
                    final SignUp signUp = new SignUp(getViewContext(),et_phone_number,btn_sign_up,getActivity().getApplication());
                    signUp.signUpUser(new isSignupOk() {
                        @Override
                        public void signUpOk(boolean isOk, int random_pass) {
                            if (isOk){
                                UserSharedPrefManager prefManager = ((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager();
                                prefManager.saveUserData(OrderActivity.VERIFICATION_CODE, String.valueOf(random_pass));
                                ((OrderActivity) Objects.requireNonNull(getActivity())).moveForward(4);


                            }
                        }
                    });
                } else {
                    Toast.makeText(getViewContext(), "شما قبلا عضو شده اید...", Toast.LENGTH_SHORT).show();
                    //proceed to confirm the order
                }
            }
        });


    }


    @Override
    public int getLayout() {
        return R.layout.layout_sign_up_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment newFragment = new SignUpFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        String phone_number = et_phone_number.getText().toString();
        if (phone_number.isEmpty() || G.currentUser == null) {
            Toast.makeText(context, getResources().getString(R.string.toast_fill_phone), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //proccedd to add order to fixers profile parse platform

            return true;
        }

    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}
