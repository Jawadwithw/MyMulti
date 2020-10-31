package com.xbbxsnia.the2806.CaptainBike.ChooseSourceContract;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProviders;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.R;

public class ChooseSourceFragment extends BaseFragment {
    private ChooseSourceViewModel viewModel;
    private LinearLayout nextBtn;
    private OnChooseSourceNextClickedListener listener;

    public ChooseSourceFragment(OnChooseSourceNextClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(this).get(ChooseSourceViewModel.class);
        nextBtn = rootView.findViewById(R.id.ll_chooseSource_nextBtn);
        nextBtn.setOnClickListener(v -> listener.onCLick());
    }

    public interface OnChooseSourceNextClickedListener {
        void onCLick();
    }

    @Override
    public int getLayout() {
        return R.layout.choose_source_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        return null;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return false;
    }
}
