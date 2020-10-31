package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.R;

import java.util.List;

public class LaundrySliderAdapter extends SliderViewAdapter<LaundrySliderAdapter.SliderVH> {
    private List<String> images;
    private Context context;

    public LaundrySliderAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public SliderVH onCreateViewHolder(ViewGroup parent) {
        return new SliderVH(LayoutInflater.from(context).inflate(R.layout.item_laundries_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(SliderVH viewHolder, int position) {
        viewHolder.bindSliderItem(images.get(position));
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    @Override
    public int getCount() {
        return images.size();
    }

    public class SliderVH extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public SliderVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_itemLaundrySlider);

        }

        public void bindSliderItem (String imageURl){
            Picasso.get().load(imageURl).into(imageView);
        }
    }

}
