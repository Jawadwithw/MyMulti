package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.MyOrdersContract.MyOrdersActivity;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

public class MyOrdersRvAdapter extends RecyclerView.Adapter<MyOrdersRvAdapter.OrderViewHolder> {
    private int pageType;
    private List<Order> orders;
    private OnCallClickListener listener;

    public MyOrdersRvAdapter(int pageType, List<Order> orders, OnCallClickListener listener) {
        this.pageType = pageType;
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bindDoingOrder(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlDeliveryInformation;
        private ImageView ivOrderImage;
        private TextView tvOrderTitle;
        private TextView tvOrderState;
        private TextView tvOrderServices;
        private TextView tvOrderPrice;
        private ImageView ivDeliverAvatar;
        private TextView tvDeliverName;
        private TextView tvDeliveryCost;
        private TextView tvDeliverPlateNumber;
        private TextView tvDeliveryType;
        private Button btnCall;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            rlDeliveryInformation = itemView.findViewById(R.id.rl_itemDoingOrder_deliveryInformation);
            ivOrderImage = itemView.findViewById(R.id.iv_itemDoingOrder_orderImage);
            tvOrderTitle = itemView.findViewById(R.id.tv_itemDoingOrder_orderTitle);
            tvOrderState = itemView.findViewById(R.id.tv_itemDoingOrder_stateText);
            tvOrderServices = itemView.findViewById(R.id.tv_itemDoingOrder_services);
            tvOrderPrice = itemView.findViewById(R.id.tv_itemDoingOrder_orderCost);
            ivDeliverAvatar = itemView.findViewById(R.id.iv_itemDoingOrder_deliverAvatar);
            tvDeliverName = itemView.findViewById(R.id.tv_itemDoingOrder_deliverName);
            tvDeliveryCost = itemView.findViewById(R.id.tv_itemDoingOrder_deliveryCost);
            tvDeliverPlateNumber = itemView.findViewById(R.id.tv_itemDoingOrder_deliverPlateNumber);
            tvDeliveryType = itemView.findViewById(R.id.tv_itemDoingOrder_deliveryType);
            btnCall = itemView.findViewById(R.id.btn_itemDoingOrder_call);
        }

        public void bindDoingOrder(Order order) {
            Picasso.get().load(order.getImageUrl()).into(ivOrderImage);
            tvOrderTitle.setText(order.getTitle());
            String state = "";
            switch (order.getState()) {
                case "sent":
                    state = "در حال انجام";
                    break;
                case "done":
                    state = "انجام شده";
                    break;
            }
            tvOrderState.setText(state);
            if (order.getServices().size() > 0) {
                StringBuilder services = new StringBuilder();
                for (int i = 1; i < order.getServices().size(); i++) {
                    services.append(order.getServices().get(i)).append("-");
                }
                services.deleteCharAt(services.length() - 1);
                tvOrderServices.setText(services.toString());
            } else {
                tvOrderServices.setText(order.getServices().get(0));
            }
            NumberFormat format = NumberFormat.getInstance();
            tvOrderPrice.setText(MessageFormat.format("{0} تومان", format.format(order.getPrice())));
            if (pageType == MyOrdersActivity.DOING_ORDERS_PAGE) {
                Picasso.get().load(order.getDeliverAvatar()).into(ivDeliverAvatar);
                tvDeliverName.setText(order.getDeliverName());
                tvDeliveryCost.setText(MessageFormat.format("{0} تومان", format.format(order.getDeliveryPrice())));
                tvDeliverPlateNumber.setText(order.getPlateNumber());
                String deliveryType = "";
                switch (order.getDeliveryType()) {
                    case "express":
                        deliveryType = "پیشتاز";
                        break;
                    case "ordinary":
                        deliveryType = "معمولی";
                        break;
                }
                tvDeliveryType.setText(deliveryType);
                btnCall.setOnClickListener(v -> listener.onClick(order.getDeliverPhoneNumber()));
            }
            if (order.getState().equals("sent"))
                rlDeliveryInformation.setVisibility(View.VISIBLE);
            else
                rlDeliveryInformation.setVisibility(View.GONE);
        }
    }

    public interface OnCallClickListener {
        void onClick(String phoneNumber);
    }

}
