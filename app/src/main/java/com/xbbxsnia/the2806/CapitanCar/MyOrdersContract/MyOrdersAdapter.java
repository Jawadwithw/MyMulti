package com.xbbxsnia.the2806.CapitanCar.MyOrdersContract;

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
import com.xbbxsnia.the2806.R;

import java.util.List;



public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.RequestViewHolder> {

    private Context context;
    private List<ParseObject> parseObjects;

    public void setRequests(Context context, List<ParseObject> parseObjects){

        this.context = context;
        this.parseObjects = parseObjects;
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_orders_row, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.tv_name.setText("نام : "+parseObjects.get(position).getString(OrderActivity.NAME));
        holder.tv_address.setText("آدرس : "+parseObjects.get(position).getString(OrderActivity.ADDRESS));
        holder.tv_phone.setText("شماره ثابت : "+parseObjects.get(position).getString(OrderActivity.ORDER_TIME));
        holder.tv_date_time.setText("سفارش برای تاریخ : "+parseObjects.get(position).getString(OrderActivity.ORDER_DATE));
        holder.tv_desc.setText("توضیحات : "+parseObjects.get(position).getString(OrderActivity.PROBLEM_DESC));

    }

    @Override
    public int getItemCount() {
        return parseObjects.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_address,tv_phone,tv_date_time,tv_desc;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_address=(TextView)itemView.findViewById(R.id.tv_address);
            tv_phone=(TextView)itemView.findViewById(R.id.tv_phone_number);
            tv_date_time=(TextView)itemView.findViewById(R.id.tv_order_time);
            tv_desc=(TextView)itemView.findViewById(R.id.tv_desc);
        }
    }
}
