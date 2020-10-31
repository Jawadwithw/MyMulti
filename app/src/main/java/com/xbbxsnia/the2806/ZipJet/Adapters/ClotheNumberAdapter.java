package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.ClothNumberModel;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

public class ClotheNumberAdapter extends RecyclerView.Adapter<ClotheNumberAdapter.ClotheNumberViewHolder> {
    private List<ClothNumberModel> clothNumbers;

    public ClotheNumberAdapter(List<ClothNumberModel> clothNumbers) {
        this.clothNumbers = clothNumbers;
    }

    @NonNull
    @Override
    public ClotheNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClotheNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothe_number, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClotheNumberViewHolder holder, int position) {
        holder.bindClotheNumber(clothNumbers.get(position));
    }

    @Override
    public int getItemCount() {
        return clothNumbers.size();
    }

    public class ClotheNumberViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivClotheIcon;
        private TextView tvClotheNumber;
        private TextView tvClotheCost;

        public ClotheNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClotheIcon = itemView.findViewById(R.id.iv_itemClotheNumber_clotheIcon);
            tvClotheNumber = itemView.findViewById(R.id.tv_itemClotheNumber_clotheNumber);
            tvClotheCost = itemView.findViewById(R.id.tv_itemClotheNumber_cost);
        }

        public void bindClotheNumber(ClothNumberModel clothNumberModel) {
            switch (clothNumberModel.getClothType()) {
                case "pant":
                    ivClotheIcon.setImageResource(R.drawable.ic_trousers_blue);
                    break;
                case "shirt":
                    ivClotheIcon.setImageResource(R.drawable.ic_shirt_blue);
                    break;
                case "hoodie":
                    ivClotheIcon.setImageResource(R.drawable.ic_hoodie_yellow);
                    break;
                case "dress":
                    ivClotheIcon.setImageResource(R.drawable.ic_dress_yellow);
                    break;
                case "shirts":
                    ivClotheIcon.setImageResource(R.drawable.ic_shirts_blue);
                    break;
            }
            tvClotheNumber.setText(MessageFormat.format("x{0}", clothNumberModel.getNumber()));
            NumberFormat format = NumberFormat.getInstance();
            format.setGroupingUsed(true);
            tvClotheCost.setText(MessageFormat.format("{0} تومان", format.format(clothNumberModel.getFinalPrice())));
        }
    }
}
