package com.xbbxsnia.the2806.Main2806.Main2806.HomeContract;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rishabhharit.roundedimageview.RoundedImageView;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OrderActivity;


import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<MenuAppData> appDatas;
    private Context context;
    private String colleague_type;

    public void setDatas(List<MenuAppData> appDatas, Context context,String colleague_type){

        this.appDatas = appDatas;
        this.context = context;

        this.colleague_type = colleague_type;
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeAdapter.HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_home_rows, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.tv_title.setText(appDatas.get(position).getTitle());
        holder.iv_image.setImageResource(appDatas.get(position).getFeatureImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (colleague_type){
                    case  ColleagueActivity.CAPTAIN_CAR:

                        context.startActivity(new Intent(context, com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.class));
                        break;
                    case ColleagueActivity.ZIP_JET:
                        context.startActivity(new Intent(context, OrderActivity.class));
                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return appDatas.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView iv_image;
        TextView tv_title;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image=(RoundedImageView) itemView.findViewById(R.id.iv_image);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
        }
    }
}
