package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parse.ParseObject;
import com.rishabhharit.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract.LaundryDetailsActivity;
import com.xbbxsnia.the2806.ZipJet.models.Laundry;

import java.text.MessageFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class LaundryAdapter extends RecyclerView.Adapter<LaundryAdapter.LaundryViewHolder> {


    private List<ParseObject> parseObjects;
    private String colleague_type;
    private Context context;
    private boolean showShimmer = true;

    public void startShimmer(){

    }
    public void setDirectories(List<ParseObject> parseObjects,String colleague_type, Context context) {


        this.parseObjects = parseObjects;
        this.colleague_type = colleague_type;
        this.context = context;
        showShimmer = false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LaundryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaundryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laundry, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull LaundryViewHolder holder, int position) {

        if (showShimmer){
            holder.shimmerFrameLayout.startShimmer();
        }else {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.laundryName.setBackground(null);
            holder.laundryAddress.setBackground(null);
            holder.laundryRate.setBackground(context.getResources().getDrawable(R.drawable.selector_btn_previous));
            holder.laundryImage.setBackground(null);
            holder.laundryAvatar.setBackground(null);

            String name= parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_TITLE);
            String address= parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_ADDRESS);
            String rate= parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_RATE);
            String imageUrl= parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_IMAGE_URL);
            String phone= parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_PHONE);
            String cooperate_username= parseObjects.get(position).getString(ColleagueActivity.COOPERATE_JOB_USERNAME);
            Picasso.get().load(imageUrl).into(holder.laundryImage);
            holder.laundryRate.setText(rate);
            holder.laundryRate.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_star),null,null,null);

            //   holder.laundryDistance.setText(MessageFormat.format("{0} کیلومتر", laundry.getDistance()));
            Picasso.get().load(imageUrl).into(holder.laundryAvatar);
            holder.laundryName.setText(name);
            holder.laundryAddress.setText(address);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LaundryDetailsActivity.class);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_TITLE,name);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_ADDRESS,address);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_RATE,rate);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_IMAGE_URL,imageUrl);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_PHONE,phone);
                    intent.putExtra(ColleagueActivity.COOPERATE_JOB_USERNAME,cooperate_username);
                    intent.putExtra(ColleagueActivity.COOPERATE_TYPE,colleague_type);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return showShimmer ? 4 : parseObjects.size();
    }

    public static class LaundryViewHolder extends RecyclerView.ViewHolder {
        private final RoundedImageView laundryImage;
        private final TextView laundryRate;
        private final CircleImageView laundryAvatar;
        private final TextView laundryName;
        private final TextView laundryAddress;
        private final ShimmerFrameLayout shimmerFrameLayout;

        public LaundryViewHolder(@NonNull View itemView) {
            super(itemView);
            laundryImage = itemView.findViewById(R.id.iv_itemLaundry_laundryImage);
            laundryRate = itemView.findViewById(R.id.tv_itemLaundry_laundryRate);
            laundryAvatar = itemView.findViewById(R.id.iv_itemLaundry_laundryAvatar);
            laundryName = itemView.findViewById(R.id.tv_itemLaundry_laundryName);
            laundryAddress = itemView.findViewById(R.id.tv_itemLaundry_laundryAddress);
            shimmerFrameLayout=(ShimmerFrameLayout)itemView.findViewById(R.id.shimmer_item_laundry);

        }

    }



}
