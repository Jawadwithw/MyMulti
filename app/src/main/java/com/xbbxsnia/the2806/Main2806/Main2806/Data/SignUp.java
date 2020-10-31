package com.xbbxsnia.the2806.Main2806.Main2806.Data;

import android.app.Application;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.xbbxsnia.the2806.CapitanCar.Data.Repository;
import com.xbbxsnia.the2806.CapitanCar.Data.SMS;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.R;

import java.util.Objects;
import java.util.Random;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SignUp {
    private final Context context;
    private final EditText et_phone_number;
    private CircularProgressButton btn_sign_up;
    private Application application;

    public SignUp(Context context, EditText et_phone_number, CircularProgressButton btn_sign_up, Application application){

        this.context = context;
        this.et_phone_number = et_phone_number;
        this.btn_sign_up = btn_sign_up;
        this.application = application;
    }

    public void signUpUser(final isSignupOk isSignupOk){
        final ParseUser user = new ParseUser();
        final Random random = new Random();
        final int random_pass = random.nextInt(90000) + 10000;
        final String phone_number = et_phone_number.getText().toString();
        user.setUsername(phone_number);
        user.setPassword(String.valueOf(random_pass));
        user.put("code", String.valueOf(random_pass));
        if (!phone_number.isEmpty()) {
            if (phone_number.startsWith("09") && phone_number.length() == 11) {
                // dialogGenerator.showDialog();
                btn_sign_up.startAnimation();
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            final Repository repository = new Repository(application);
                            SMS sms = new SMS();
                            sms.setTo_number(phone_number);
                            sms.setVerification_code(String.valueOf(random_pass));
                            repository.sendSMS1(phone_number, String.valueOf(random_pass)).enqueue(new Callback<SMS>() {
                                @Override
                                public void onResponse(Call<SMS> call, Response<SMS> response) {
                                    if (response.message().equals("OK")) {
                                        btn_sign_up.doneLoadingAnimation(R.color.color_blue, BitmapFactory.decodeResource(application.getResources(), R.drawable.ic_done_white_48dp));



                                      isSignupOk.signUpOk(true,random_pass);
                                    } else {
                                        //sms did not sent!

                                    }
                                }
                                @Override
                                public void onFailure(Call<SMS> call, Throwable t) {
                                    Log.d(TAG, "onResponse2: " + t);
                                }
                            });
                        } else {
                            //  dialogGenerator.dismissDialog();
                            if (e.getCode() == ParseException.CONNECTION_FAILED) {
                                Toast.makeText(context, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();

                            } else {

                                //already exist then send sms and verification stuff..
                                Toast.makeText(context, "این شماره قبلا ثبت نام کرده است.", Toast.LENGTH_SHORT).show();

                            }
                            btn_sign_up.revertAnimation(new Function0<Unit>() {
                                @Override
                                public Unit invoke() {
                                    return null;
                                }
                            });
                        }
                    }
                });

            } else {
                Toast.makeText(context, "لطفا شماره تلفن را صحیح وارد نمایید.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "لطفا تمامی فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
        }
    }
}
