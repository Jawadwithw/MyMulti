package com.xbbxsnia.the2806.ZipJet.OrderContract.Invoice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.Main2806.Main2806.MainContract.MainActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.StartContract.StartActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.ClotheNumberAdapter;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnNextOrBackClickedCallBack;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnOrderConfirmed;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Objects;

public class InvoiceFragment extends BaseFragment {
    private TextView tvFinalCost;
    private RadioButton rbOrdinary;
    private RadioButton rbExpress;
    private TextView tvFinalCostPlusDelivery;
    private Button nextBtn;
    private RadioGroup rgDeliveryType;
    private NumberFormat myFormat;
    private InvoiceViewModel viewModel;
    private Order order;

    public InvoiceFragment(Order order) {
        this.order = order;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvFinalCost.setText(MessageFormat.format("{0} تومان", myFormat.format(order.getPrice())));
        tvFinalCostPlusDelivery.setText(MessageFormat.format("{0} تومان", myFormat.format(order.getPrice())));
    }

    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
        initRecyclerView();
        myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        tvFinalCost = rootView.findViewById(R.id.tv_invoiceActivity_finalCost);
        tvFinalCostPlusDelivery = rootView.findViewById(R.id.tv_invoiceActivity_finalCostPlusDelivery);
        rbOrdinary = rootView.findViewById(R.id.rb_invoiceActivity_ordinaryDelivery);
        rbExpress = rootView.findViewById(R.id.rb_invoiceActivity_expressDelivery);
        rgDeliveryType = rootView.findViewById(R.id.rg_invoiceActivity);
        nextBtn = rootView.findViewById(R.id.btn_invoiceActivity_pay);

        tvFinalCost.setText(MessageFormat.format("{0} تومان", myFormat.format(order.getPrice())));
        nextBtn.setText(MessageFormat.format("پرداخت {0} تومان", myFormat.format(order.getPrice())));

        rbOrdinary.setOnClickListener(v -> {
            order.setDeliveryType("ordinary");
            order.setDeliveryPrice(7000);
            tvFinalCostPlusDelivery.setText(MessageFormat.format("{0} تومان", myFormat.format(order.getPrice() + 7000)));
            nextBtn.setText(MessageFormat.format("پرداخت {0} تومان", myFormat.format(order.getPrice() + 7000)));
        });

        rbExpress.setOnClickListener(v -> {
            order.setDeliveryType("express");
            order.setDeliveryPrice(15000);
            tvFinalCostPlusDelivery.setText(MessageFormat.format("{0} تومان", myFormat.format(order.getPrice() + 15000)));
            nextBtn.setText(MessageFormat.format("پرداخت {0} تومان", myFormat.format(order.getPrice() + 15000)));
        });

        nextBtn.setOnClickListener(v -> {
            if (isConfirmed(getContext())) {
                viewModel.postOrder(rootView.getContext(), order, new OnOrderConfirmed() {
                    @Override
                    public void orderConfirmed(boolean confirmed) {
                        if (confirmed){
                            getActivity().startActivity(new Intent(rootView.getContext(), MainActivity.class));
                            Objects.requireNonNull(getActivity()).finish();
                        }
                    }
                });
            } else
                Toast.makeText(getContext(), R.string.please_choose_delivery_type, Toast.LENGTH_SHORT).show();
        });

    }

    public void initRecyclerView() {
        RecyclerView rvNumbers = rootView.findViewById(R.id.rv_invoiceActivity_numberOfClothes);
        rvNumbers.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvNumbers.setAdapter(new ClotheNumberAdapter(order.getClothesModel()));
    }

    @Override
    public int getLayout() {
        return R.layout.invoice_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        return null;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return rgDeliveryType.getCheckedRadioButtonId() != -1;
    }
}
