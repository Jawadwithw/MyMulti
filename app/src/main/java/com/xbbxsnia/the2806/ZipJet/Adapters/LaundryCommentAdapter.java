package com.xbbxsnia.the2806.ZipJet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract.LaundryDetailsActivity;
import com.xbbxsnia.the2806.ZipJet.models.LaundryComment;

import java.util.List;

public class LaundryCommentAdapter extends RecyclerView.Adapter<LaundryCommentAdapter.LaundryCommentViewHolder> {
    private List<LaundryComment> comments;
    private Context context;
    private List<ParseObject> parseObjects;


    public void setComments(Context context, List<ParseObject> parseObjects){
        this.context = context;
        this.parseObjects = parseObjects;
    }

    @NonNull
    @Override
    public LaundryCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaundryCommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laundry_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LaundryCommentViewHolder holder, int position) {
        holder.binComment(parseObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return parseObjects.size();
    }

    public static class LaundryCommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUseName;
        private final TextView tvDate;
        private final RatingBar rate;
        private final TextView tvBody;

        public LaundryCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUseName = itemView.findViewById(R.id.tv_itemLaundryComment_userName);
            tvDate = itemView.findViewById(R.id.tv_itemLaundryComment_date);
            rate = itemView.findViewById(R.id.rb_itemLaundryComment);
            tvBody = itemView.findViewById(R.id.tv_itemLaundryComment_body);
        }

        public void binComment(ParseObject parseObject) {
            tvUseName.setText(parseObject.getString(LaundryDetailsActivity.CMM_NAME));
            tvDate.setText(parseObject.getString(LaundryDetailsActivity.CMM_DATE));
            rate.setRating(Float.parseFloat(parseObject.getString(LaundryDetailsActivity.CMM_RATE)));
            tvBody.setText(parseObject.getString(LaundryDetailsActivity.CMM_BODY));

        }
    }

}
