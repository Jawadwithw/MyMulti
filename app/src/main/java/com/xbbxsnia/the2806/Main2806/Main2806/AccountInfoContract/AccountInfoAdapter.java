package com.xbbxsnia.the2806.Main2806.Main2806.AccountInfoContract;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.AllContract.AllOrdersActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueOrders.ColleagueOrdersActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.Main2806.Main2806.ProfileContract.ProfileActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.RulesContract.RulesActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.StartContract.StartActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.WalletContract.WalletActivity;
import com.xbbxsnia.the2806.R;

import java.util.List;


public class AccountInfoAdapter extends RecyclerView.Adapter<AccountInfoAdapter.AccountInfoViewHolder> {

    private Context context;
    private List<MenuAppData> appDatas;
    private LinearLayout linear_support;


    public void setAccountInfo(Context context, List<MenuAppData> appDatas, LinearLayout linear_support) {

        this.context = context;
        this.appDatas = appDatas;
        this.linear_support = linear_support;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AccountInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountInfoAdapter.AccountInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.more_fragment_rows, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountInfoViewHolder holder, final int position) {
        MenuAppData appData = appDatas.get(position);
        holder.tv_more.setText(appData.getTitle());
        holder.iv_more.setImageResource(appData.getFeatureImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        context.startActivity(new Intent(context, WalletActivity.class));
                        break;
                    case 1:
                        if (G.currentUser != null) {
                            Intent intent = new Intent(context, ColleagueActivity.class);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.sign_up_first), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, StartActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                        break;

                    case 2:
                        if (G.currentUser != null) {
                            Intent intent = new Intent(context, ColleagueOrdersActivity.class);
                            context.startActivity(intent);

                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.sign_up_first), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, StartActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                        break;

                    case 3:
                        linear_support.setVisibility(View.VISIBLE);
                        linear_support.setAlpha(0.0f);

                        linear_support.animate()
                                .translationY(10)
                                .alpha(1.0f)
                                .setListener(null);
                        break;
                    case 4:
                        context.startActivity(new Intent(context, RulesActivity.class));
                        break;
                    case 5:
                        context.startActivity(new Intent(context, AllOrdersActivity.class));
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return appDatas.size();
    }

    public class AccountInfoViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_more, ic_left;
        TextView tv_more;
        private LinearLayout linear_support;

        public AccountInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_more = (ImageView) itemView.findViewById(R.id.iv_more);
            ic_left = (ImageView) itemView.findViewById(R.id.ic_left);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }

}
