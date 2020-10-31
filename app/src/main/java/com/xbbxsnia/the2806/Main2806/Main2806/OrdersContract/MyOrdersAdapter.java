package com.xbbxsnia.the2806.Main2806.Main2806.OrdersContract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.rishabhharit.roundedimageview.RoundedImageView;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.R;

import java.util.List;


public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.RequestViewHolder> {

    private Context context;
    private List<ParseObject> parseObjects;

    public void setRequests(Context context, List<ParseObject> parseObjects){

        this.context = context;
        this.parseObjects = parseObjects;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrdersAdapter.RequestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_orders_rows,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        String order_for = parseObjects.get(position).getString(OrderActivity.ORDER_FOR);
        String order_status = parseObjects.get(position).getString(OrderActivity.ORDER_STATUS);
        switch (order_for){
            case ColleagueActivity.CAPTAIN_CAR:
                holder.tv_title.setText(context.getResources().getString(R.string.captan_car));
                holder.roundedImageView.setImageResource(R.drawable.capitan_car);

                break;
            case ColleagueActivity.ZIP_JET:
                holder.tv_title.setText(context.getResources().getString(R.string.zip_jet));
                holder.roundedImageView.setImageResource(R.drawable.zip_jet);
                break;
        }

        switch (order_status){
            case OrderActivity.PENDING:
                holder.tv_captain.setText("کاپیتان: "+context.getResources().getString(R.string.pending));
                holder.tv_status.setText(context.getResources().getString(R.string.pending));
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.zipJetColorGold));

                break;
            case OrderActivity.DONE:
                holder.tv_captain.setText("کاپیتان: "+parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_TITLE));
                holder.tv_status.setText(context.getResources().getString(R.string.done));
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.zipJetColorPrimary));


                break;
            case OrderActivity.ARRIVAL:
                holder.tv_captain.setText("کاپیتان: "+parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_TITLE));
                holder.tv_status.setText(context.getResources().getString(R.string.arrival));
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.wallet_color));

                break;

        }
        holder.tv_date.setText(parseObjects.get(position).getString(OrderActivity.ORDER_DATE));
        holder.tv_time.setText(parseObjects.get(position).getString(OrderActivity.ORDER_TIME));




    }

    @Override
    public int getItemCount() {
        return parseObjects.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_captain,tv_status,tv_time,tv_date;
        RoundedImageView roundedImageView;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.tv_order_type);
            tv_captain=(TextView)itemView.findViewById(R.id.tv_captain);
            tv_status=(TextView)itemView.findViewById(R.id.tv_order_status);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            tv_time=(TextView)itemView.findViewById(R.id.tv_time);
            roundedImageView=(RoundedImageView) itemView.findViewById(R.id.iv_order_logo);
        }
    }
}
