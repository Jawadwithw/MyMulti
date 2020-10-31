package com.xbbxsnia.the2806.CapitanCar.FinishContract;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.CreateOrderNotification;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.R;

import java.util.Objects;

import static android.content.ContentValues.TAG;


public class FinishFragment extends BaseFragment implements BaseView {


    @Override
    public void setupViews() {
        Log.d("FINISH", "setupViews: ");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("FINISH", "resume: ");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("FINISH", "start: ");

    }

    @Override
    public int getLayout() {
        return R.layout.layout_finish_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        FinishFragment newFragment = new FinishFragment();
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
