package com.xbbxsnia.the2806.Main2806.Main2806.AccountInfoContract;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.MenuAppData;
import com.xbbxsnia.the2806.Main2806.Main2806.MainContract.MainActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.ProfileContract.ProfileActivity;
import com.xbbxsnia.the2806.R;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class AccountInfoFragment extends BaseFragment implements BaseView {
    private List<MenuAppData> menuAppData;
    private TextView tv_name, tv_user_name, tv_call, tv_telegram;
    private UserSharedPrefManager prefManager;
    private ImageView iv_profile;
    private CircleImageView iv_account_info_profile;
    private LinearLayout linear_support;
    private RelativeLayout more_relative;

    @Override
    public void setupViews() {
        linear_support = (LinearLayout) rootView.findViewById(R.id.linear_support);
        more_relative = (RelativeLayout) rootView.findViewById(R.id.more_relative);
        RecyclerView rv_vertical = (RecyclerView) rootView.findViewById(R.id.rv_more);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getViewContext());
        rv_vertical.setLayoutManager(linearLayoutManager);
        AccountInfoAdapter accountInfoAdapter = new AccountInfoAdapter();
        accountInfoAdapter.setAccountInfo(getViewContext(), DataFakeGeneratorAccount.getMoreDatas(getViewContext()), linear_support);
        rv_vertical.setAdapter(accountInfoAdapter);
        prefManager = ((MainActivity) Objects.requireNonNull(getActivity())).getSharedPref(getViewContext());
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_user_name = (TextView) rootView.findViewById(R.id.tv_user_name);
        iv_account_info_profile = (CircleImageView) rootView.findViewById(R.id.iv_profile_menu);
        iv_profile = (ImageView) rootView.findViewById(R.id.iv_edit_profile);
        tv_call = (TextView) rootView.findViewById(R.id.tv_call);
        tv_telegram = (TextView) rootView.findViewById(R.id.tv_telegram);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent
                Intent intent = new Intent(getViewContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        more_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_support.getVisibility() == View.VISIBLE) {
                    linear_support.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linear_support.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });


        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + "09120396076";
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse(uri));
                startActivity(intent1);
            }
        });

        tv_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://telegram.com/jawadabbasnia"));
                getViewContext().startActivity(browserIntent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        String name = prefManager.getUserData(OrderActivity.NAME);
        String gender = prefManager.getUserData(OrderActivity.GENDER);
        if (!name.equals("")) {
            tv_name.setText(name + " " + prefManager.getUserData(OrderActivity.LAST_NAME));
            if (gender.equals("آقا")) {
                iv_account_info_profile.setImageResource(R.drawable.ic_male);

            } else if (gender.equals("خانم")) {
                iv_account_info_profile.setImageResource(R.drawable.ic_female);

            }
        }
        if (G.currentUser != null) {
            tv_user_name.setText(G.currentUser.getUsername());
        } else {
            tv_user_name.setText(getResources().getString(R.string.tv_your_address_2));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.layout_account_info;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        AccountInfoFragment newFragment = new AccountInfoFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        return false;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


}
