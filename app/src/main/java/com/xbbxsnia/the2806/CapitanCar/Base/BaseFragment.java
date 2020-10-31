package com.xbbxsnia.the2806.CapitanCar.Base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayout(),container,false);
            setupViews();
        }
        return rootView;
    }

    public abstract void setupViews();
    public abstract int getLayout();
    public abstract BaseFragment newInstance();
    public abstract boolean isConfirmed(Context context);
}
