package com.xbbxsnia.the2806.CapitanCar.DirectoryContract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract.LaundryDetailsActivity;

import java.util.List;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder> {


    private Context context;
    private String colleague_type;
    private List<ParseObject> parseObjects;
    private boolean showShimmer = true;

    public void startShimmer(){

    }
    public void setDirectories(Context context, String colleague_type, List<ParseObject> parseObjects) {

        this.context = context;
        this.colleague_type = colleague_type;
        this.parseObjects = parseObjects;
        showShimmer = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DirectoryAdapter.DirectoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_directory_rows, parent, false));

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull DirectoryViewHolder holder, int position) {
        if (showShimmer){
        holder.shimmerFrameLayout.startShimmer();
        }else {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.tv_name.setBackground(null);
            holder.tv_address.setBackground(null);
            holder.iv_directory_image.setBackground(null);
            holder.iv_map_location.setBackground(null);
            holder.tv_rate.setBackground(null);

            String name = parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_TITLE);
            String address = parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_ADDRESS);
            String rate = parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_RATE);
            String imageUrl = parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_IMAGE_URL);
            String phone = parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_PHONE);
            String cooperate_username = parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_USERNAME);
            holder.tv_name.setText(name);
            holder.tv_address.setText(address);
            holder.tv_rate.setText(rate);
            holder.tv_rate.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_star),null,null,null);
            Picasso.get().load(imageUrl).into(holder.iv_directory_image);
            switch (colleague_type) {
                case ColleagueActivity.CAPTAIN_CAR:
                    holder.iv_map_location.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_location_red));

                    break;
                case ColleagueActivity.ZIP_JET:
                    holder.iv_map_location.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_location_green));

                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LaundryDetailsActivity.class);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_TITLE, name);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_ADDRESS, address);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_RATE, rate);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_IMAGE_URL, imageUrl);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_PHONE, phone);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_USERNAME, cooperate_username);
                    intent.putExtra(ColleagueActivity.COOPERATE_TYPE, colleague_type);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return showShimmer ? 5 : parseObjects.size();
    }

    public static class DirectoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_address, tv_rate;
        ImageView iv_directory_image, iv_map_location;
        ShimmerFrameLayout shimmerFrameLayout;
        public DirectoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name_directory);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address_directory);
            tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
            iv_directory_image = (ImageView) itemView.findViewById(R.id.iv_directory_image);
            iv_map_location = (ImageView) itemView.findViewById(R.id.iv_location);
            shimmerFrameLayout=(ShimmerFrameLayout)itemView.findViewById(R.id.shimmer_layout);
        }
    }

}
