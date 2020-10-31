package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.rishabhharit.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.Laundry;

import java.text.MessageFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LaundriesOnMapAdapter extends RecyclerView.Adapter<LaundriesOnMapAdapter.LaundriesOnMapViewHolder> {
    private List<Laundry> laundries;
    private OnItemClickListener listener;

    public LaundriesOnMapAdapter(List<Laundry> laundries, OnItemClickListener listener) {
        this.laundries = laundries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LaundriesOnMapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaundriesOnMapViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laundry_white_background, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LaundriesOnMapViewHolder holder, int position) {
        holder.bindLaundry(laundries.get(position));
    }

    @Override
    public int getItemCount() {
        return laundries.size();
    }

    public class LaundriesOnMapViewHolder extends RecyclerView.ViewHolder {
        private CardView cvRoot;
        private RoundedImageView laundryImage;
        private TextView laundryRate;
        private TextView laundryDistance;
        private CircleImageView laundryAvatar;
        private TextView laundryName;
        private TextView laundryAddress;

        public LaundriesOnMapViewHolder(@NonNull View itemView) {
            super(itemView);
            cvRoot = itemView.findViewById(R.id.cv_itemLaundryWhiteBackground_root);
            laundryImage = itemView.findViewById(R.id.iv_itemLaundryWhiteBackground_laundryImage);
            laundryRate = itemView.findViewById(R.id.tv_itemLaundryWhiteBackground_laundryRate);
            laundryDistance = itemView.findViewById(R.id.tv_itemLaundryWhiteBackground_laundryDistance);
            laundryAvatar = itemView.findViewById(R.id.iv_itemLaundryWhiteBackground_laundryAvatar);
            laundryName = itemView.findViewById(R.id.tv_itemLaundryWhiteBackground_laundryName);
            laundryAddress = itemView.findViewById(R.id.tv_itemLaundryWhiteBackground_laundryAddress);
        }

        public void bindLaundry(Laundry laundry){
            Picasso.get().load(laundry.getImagesUrl().get(0)).into(laundryImage);
            laundryRate.setText(String.valueOf(laundry.getRate()));
            laundryDistance.setText(MessageFormat.format("{0} کیلومتر", laundry.getDistance()));
            Picasso.get().load(laundry.getAvatarUrl()).into(laundryAvatar);
            laundryName.setText(laundry.getName());
            laundryAddress.setText(laundry.getAddress());
            cvRoot.setOnClickListener(v -> listener.onClick(laundry.getCode()));
        }
    }

    public interface OnItemClickListener {
        void onClick(String code);
    }

}
