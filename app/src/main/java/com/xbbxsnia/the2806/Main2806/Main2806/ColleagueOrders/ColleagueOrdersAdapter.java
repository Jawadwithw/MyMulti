package com.xbbxsnia.the2806.Main2806.Main2806.ColleagueOrders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.AllContract.AllOrdersAdapter;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.R;

import java.util.List;

public class ColleagueOrdersAdapter extends RecyclerView.Adapter<ColleagueOrdersAdapter.ColleagueOrdersViewHolder> {

    private Context context;
    private List<ParseObject> parseObjects;

    public void setOrders(Context context, List<ParseObject> parseObjects){

        this.context = context;
        this.parseObjects = parseObjects;
    }

    @NonNull
    @Override
    public ColleagueOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColleagueOrdersAdapter.ColleagueOrdersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_orders_row, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ColleagueOrdersViewHolder holder, int position) {
        String date_time = parseObjects.get(position).getString(OrderActivity.ORDER_DATE) + "-" + parseObjects.get(position).getString(OrderActivity.ORDER_TIME);
        String colleague_type = parseObjects.get(position).getString(OrderActivity.ORDER_FOR);
        switch (colleague_type) {
            case ColleagueActivity.CAPTAIN_CAR:
                String full_name = parseObjects.get(position).getString(OrderActivity.NAME) + " " + parseObjects.get(position).getString(OrderActivity.LAST_NAME);
                String car_detail = parseObjects.get(position).getString(OrderActivity.CAR_BRAND) + "-" + parseObjects.get(position).getString(OrderActivity.CARD_MODEL);

                holder.order_title.setText(parseObjects.get(position).getString(OrderActivity.PROBLEM_TYPE));
                holder.tv_detail.setText("جزپیات خودرو : " + car_detail);
                holder.tv_name.setText("نام کامل : " + full_name);

                break;
            case ColleagueActivity.ZIP_JET:
                String cloth_detail = parseObjects.get(position).getString(OrderActivity.ORDER_CLOTH_TYPE) + "-" + parseObjects.get(position).getString(OrderActivity.ORDER_COLOR);
                holder.tv_name.setText("نام کامل : " + parseObjects.get(position).getString(OrderActivity.NAME));
                holder.tv_detail.setText("جزپیات  : " + cloth_detail);

                break;
        }

        holder.tv_desc.setText("توضیحات : " + parseObjects.get(position).getString(OrderActivity.PROBLEM_DESC));
        holder.tv_phone.setText("شماره همراه : " + parseObjects.get(position).getString(OrderActivity.ORDER_FROM));
        holder.tv_address.setText("آدرس : " + parseObjects.get(position).getString(OrderActivity.TOTAL_ADDRESS));
        holder.tv_date_time.setText("سفارش برای  : " + date_time);
    }

    @Override
    public int getItemCount() {
        return parseObjects.size();
    }

    public static class ColleagueOrdersViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_address, tv_phone, tv_date_time, tv_desc,order_title,tv_detail;
        public ColleagueOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone_number);
            tv_date_time = (TextView) itemView.findViewById(R.id.tv_order_time);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            order_title = (TextView) itemView.findViewById(R.id.tv_order_title);
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }
}
