package com.xbbxsnia.the2806.CapitanCar.VerificationContract;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.CreateOrderNotification;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.R;

import java.util.Objects;

public class VerificationFragment extends BaseFragment implements BaseView {

    @Override
    public void setupViews() {

        final PinEntryEditText entryEditText=(PinEntryEditText)rootView.findViewById(R.id.txt_pin_entry);
        entryEditText.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                UserSharedPrefManager prefManager=((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager();
                if (str.toString().equals(prefManager.getUserData(OrderActivity.VERIFICATION_CODE))){
                    //procced to finish fragment
                    G.currentUser = ParseUser.getCurrentUser();
                    ((OrderActivity)getActivity()).moveForward(5);

                }else {
                    entryEditText.setText(null);
                    Toast.makeText(getViewContext(), "کد فعالسازی و عدد وارد شده یکسان  نمی باشند..", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public int getLayout() {
        return R.layout.layout_verification_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        VerificationFragment newFragment = new VerificationFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return false;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


}
