package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.OrderTime;

import java.util.List;

public class OrderDayAdapter extends RecyclerView.Adapter<OrderDayAdapter.OrderTimeViewHolder> {
    private List<OrderTime> orderTimes;
    private OnDayCardViewClickListener listener;
    private int selectedPosition = -1;

    public OrderDayAdapter(List<OrderTime> orderTimes, OnDayCardViewClickListener listener) {
        this.orderTimes = orderTimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderTimeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_order_day, parent, false));
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
        private TextView tvDay;
        private TextView tvDate;
        private MaterialCardView cvContainer;
        private int price;
        private boolean isDayClicked = false;

        public OrderTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            tvDay = itemView.findViewById(R.id.tv_itemChooseOrderDay_day);
            cvContainer = itemView.findViewById(R.id.cv_itemChooseOrderDay_container);

            tvDate = itemView.findViewById(R.id.tv_itemChooseOrderDay_date);
        }

        public void bindOrderTime(OrderTime orderTime) {
            tvDay.setText(orderTime.getTime());
            tvDate.setText(orderTime.getCost());

            cvContainer.setOnClickListener(v -> {
                if (selectedPosition == getAdapterPosition()) {
                    selectedPosition = -1;
                    isDayClicked = false;
                } else {
                    selectedPosition = getAdapterPosition();
                    isDayClicked = true;
                }
                switch (getAdapterPosition()){
                    case 0:
                        if (isDayClicked) price = 20000;
                        else price = 0;
                        break;
                    case 1:
                        if (isDayClicked) price = 10000;
                        else price = 0;
                        break;
                    case 2:
                        price = 0;
                        break;
                }
                listener.onClick(isDayClicked, tvDay.getText().toString(), price);
                notifyDataSetChanged();
            });

            if (getAdapterPosition() == selectedPosition) {
                cvContainer.setStrokeColor(itemView.getResources().getColor(R.color.zipJetColorPrimary));
                cvContainer.setStrokeWidth(3);
                tvDay.setTextColor(itemView.getResources().getColor(R.color.zipJetColorPrimary));
                tvDate.setTextColor(itemView.getResources().getColor(R.color.zipJetColorPrimary));
            } else {
                cvContainer.setStrokeColor(itemView.getResources().getColor(R.color.white));
                cvContainer.setStrokeWidth(0);
                tvDay.setTextColor(itemView.getResources().getColor(R.color.zipJetColorDark));
                tvDate.setTextColor(Color.parseColor("#A3A3A3"));
            }
        }
    }

    public interface OnDayCardViewClickListener {
        void onClick(boolean b, String text, int price);
    }
}
