package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.models.ClothNumberModel;

import java.util.ArrayList;
import java.util.List;

public class ClotheTypeNumberAdapter extends RecyclerView.Adapter<ClotheTypeNumberAdapter.ClotheTypeNumberViewHolder> {
    private List<ClothNumberModel> clothes;
    private List<ClothNumberModel> selectedClothes = new ArrayList<>();

    public ClotheTypeNumberAdapter(List<ClothNumberModel> clothes) {
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public ClotheTypeNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClotheTypeNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothe_type_number, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClotheTypeNumberViewHolder holder, int position) {
        holder.bindClotheTypeNumber(clothes.get(position));
    }

    public List<ClothNumberModel> getSelectedClothes(){
        return selectedClothes;
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ClotheTypeNumberViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivClotheIcon;
        private CardView cvPlus;
        private LinearLayout llNumberIndicator;
        private ImageView ivPlus;
        private ImageView ivRemove;
        private TextView tvClotheNumber;
        private boolean isPlusClicked = false;
        private int number = 0;

        public ClotheTypeNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClotheIcon = itemView.findViewById(R.id.iv_itemClotheTypeNumber_clotheIcon);
            cvPlus = itemView.findViewById(R.id.cv_itemClotheTypeNumber_plusContainer);
            llNumberIndicator = itemView.findViewById(R.id.ll_itemClotheTypeNumber_numberIndicator);
            ivPlus = itemView.findViewById(R.id.iv_itemClotheTypeNumber_plus);
            ivRemove = itemView.findViewById(R.id.iv_itemClotheTypeNumber_remove);
            tvClotheNumber = itemView.findViewById(R.id.tv_itemClotheTypeNumber_clotheNumber);
        }

        public void bindClotheTypeNumber(ClothNumberModel model) {
            ivClotheIcon.setImageResource(Integer.parseInt(model.getImageUrl()));
            cvPlus.setOnClickListener(v -> {
                cvPlus.setVisibility(View.GONE);
                llNumberIndicator.setVisibility(View.VISIBLE);
                number++;
                tvClotheNumber.setText(String.valueOf(number));
                isPlusClicked = true;
                selectedClothes.add(model);
                model.setNumber(number);
                model.setFinalPrice(number * model.getPrice());
            });

            ivPlus.setOnClickListener(v -> {
                number++;
                tvClotheNumber.setText(String.valueOf(number));
                model.setNumber(number);
                model.setFinalPrice(number * model.getPrice());
            });

            ivRemove.setOnClickListener(v -> {
                if (number > 1) {
                    number--;
                    tvClotheNumber.setText(String.valueOf(number));
                    model.setNumber(number);
                } else {
                    cvPlus.setVisibility(View.VISIBLE);
                    llNumberIndicator.setVisibility(View.GONE);
                    selectedClothes.remove(model);
                    isPlusClicked = false;
                }
            });

        }

    }

}
