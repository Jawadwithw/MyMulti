package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.OrderTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderTimeAdapter extends RecyclerView.Adapter<OrderTimeAdapter.OrderTimeViewHolder> {
    private List<OrderTime> orderTimes;
    private OnTimeCardViewClickListener listener;
    private int selectedPosition = -1;

    public OrderTimeAdapter(List<OrderTime> orderTimes, OnTimeCardViewClickListener listener) {
        this.orderTimes = orderTimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderTimeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_order_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderTimeViewHolder holder, int position) {
        holder.bindOrderTime(orderTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return orderTimes.size();
    }

    public class OrderTimeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private MaterialCardView cvContainer;
        private boolean isTimeClicked = false;

        public OrderTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            tvTime = itemView.findViewById(R.id.tv_itemChooseOrderTime);
            cvContainer = itemView.findViewById(R.id.cv_itemChooseOrderTime_container);
        }

        public void bindOrderTime(OrderTime orderTime) {
            tvTime.setText(orderTime.getTime());

            cvContainer.setOnClickListener(v -> {
                if (selectedPosition == getAdapterPosition()) {
                    selectedPosition = -1;
                    isTimeClicked = false;
                } else {
                    selectedPosition = getAdapterPosition();
                    isTimeClicked = true;
                }
                notifyDataSetChanged();
                listener.onClick(isTimeClicked, tvTime.getText().toString());
            });

            if (getAdapterPosition() == selectedPosition) {
                cvContainer.setStrokeColor(itemView.getResources().getColor(R.color.zipJetColorPrimary));
                cvContainer.setStrokeWidth(3);
                tvTime.setTextColor(itemView.getResources().getColor(R.color.zipJetColorPrimary));
            } else {
                cvContainer.setStrokeColor(itemView.getResources().getColor(R.color.white));
                cvContainer.setStrokeWidth(0);
                tvTime.setTextColor(itemView.getResources().getColor(R.color.zipJetColorDark));
            }
        }
    }

    public interface OnTimeCardViewClickListener {
        void onClick(boolean b, String text);
    }
}
