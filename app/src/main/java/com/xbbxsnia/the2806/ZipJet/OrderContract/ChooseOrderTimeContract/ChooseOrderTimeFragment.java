package com.xbbxsnia.the2806.ZipJet.OrderContract.ChooseOrderTimeContract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.OrderDayAdapter;
import com.xbbxsnia.the2806.ZipJet.Adapters.OrderTimeAdapter;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnNextOrBackClickedCallBack;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.text.MessageFormat;
import java.text.NumberFormat;

public class ChooseOrderTimeFragment extends BaseFragment {
    private ChooseOrderTimeViewModel viewModel;
    private Button nextBtn;
    private OnNextOrBackClickedCallBack callBack;
    private CardView backArrow;
    private Order order;
    private TextView tvChooseLocation, tvDeliveryExtraCost;
    private RelativeLayout rlChooseLocation;
    private EditText etAddress;

    public ChooseOrderTimeFragment(OnNextOrBackClickedCallBack callBack, Order order) {
        this.callBack = callBack;
        this.order = order;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            order.setUserLat(data.getDoubleExtra("lat", 0));
            order.setUserLng(data.getDoubleExtra("lng", 0));
            tvChooseLocation.setText(R.string.choose_location_success_message);
            tvChooseLocation.setTextColor(getResources().getColor(R.color.zipJetColorPrimary));
        }
    }

    @Override
    public void setupViews() {
        viewModel = ViewModelProviders.of(this).get(ChooseOrderTimeViewModel.class);
        nextBtn = rootView.findViewById(R.id.btn_chooseOrderTime_next);
        backArrow = rootView.findViewById(R.id.cv_chooseOrderTimeFragment_backArrow);
        tvChooseLocation = rootView.findViewById(R.id.tv_chooseOrderTime_chooseLocation);
        tvDeliveryExtraCost = rootView.findViewById(R.id.tv_chooseOrderTime_deliveryExtraCost);
        rlChooseLocation = rootView.findViewById(R.id.rl_chooseOrderTime_chooseLocation);
        etAddress = rootView.findViewById(R.id.et_chooseOrderTime_userAddress);
        backArrow.setOnClickListener(v -> callBack.onBackClicked());
        daysInit();
        timesInit();

        tvDeliveryExtraCost.setText("0 تومان");

        rlChooseLocation.setOnClickListener(v -> {
            startActivityForResult(new Intent(getActivity(), ChooseLocationActivity.class), 1002);
        });

        nextBtn.setOnClickListener(v -> {
            if (isConfirmed(getContext())) {
                order.setUserAddress(etAddress.getText().toString());
                callBack.onNextClick(2, order);
            } else
                Toast.makeText(getContext(), "لطفا اطلاعات خواسته شده را وارد کنید.", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getLayout() {
        return R.layout.choose_order_time_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        return null;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return order.getTime() != null && order.getDate() != null && order.getUserLat() > 0 && order.getUserLng() > 0 && !etAddress.getText().toString().equals("");
    }

    public void daysInit() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_chooseOrderTime_day);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        OrderDayAdapter adapter = new OrderDayAdapter(viewModel.getDays(), (b, text, price) -> {
            if (b) {
                order.setDate(text);
                NumberFormat format = NumberFormat.getInstance();
                format.setGroupingUsed(true);
                order.setPrice(price);
                tvDeliveryExtraCost.setText(MessageFormat.format("{0} تومان", format.format(price)));
            } else {
                order.decreasePrice(price);
                tvDeliveryExtraCost.setText("0 تومان");
                order.setDate(null);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void timesInit() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_chooseOrderTime_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        OrderTimeAdapter adapter = new OrderTimeAdapter(viewModel.getHours(), (b, text) -> {
            if (b)
                order.setTime(text);
            else
                order.setTime(null);
        });
        recyclerView.setAdapter(adapter);
    }
}
