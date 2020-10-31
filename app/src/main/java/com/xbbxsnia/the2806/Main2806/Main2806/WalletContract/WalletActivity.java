package com.xbbxsnia.the2806.Main2806.Main2806.WalletContract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseActivity;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.R;

public class WalletActivity extends BaseActivity implements BaseView {
    private TextView tv_amount, tv_sub_amount_0, tv_sub_amount_1, tv_sub_amount_2, tv_amount_changing;
    private Button btn_add, btn_minus;
    String changing_amount;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setupViews();
    }

    @Override
    public void setupViews() {
        tv_amount = findViewById(R.id.tv_amount);
        tv_sub_amount_0 = findViewById(R.id.tv_sub_amount_0);
        tv_sub_amount_1 = findViewById(R.id.tv_sub_amount_1);
        tv_sub_amount_2 = findViewById(R.id.tv_sub_amount_2);
        tv_amount_changing = findViewById(R.id.tv_amount_changing);
        btn_add = findViewById(R.id.btn_add);
        btn_minus = findViewById(R.id.btn_minus);

        changing_amount = tv_sub_amount_1.getText().toString();
        tv_amount_changing.setText(changing_amount);


        amount = Integer.parseInt(splitSentence(changing_amount));
        btn_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                amount = amount + 5000;
                tv_amount_changing.setText(String.valueOf(amount) + " تومان");
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (amount > 0) {
                    amount = amount - 5000;
                    tv_amount_changing.setText(String.valueOf(amount) + " تومان");

                }
            }
        });

        tv_sub_amount_0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tv_sub_amount_0.setTextColor(getResources().getColor(R.color.wallet_color));
                tv_sub_amount_1.setTextColor(getResources().getColor(R.color.gray));
                tv_sub_amount_2.setTextColor(getResources().getColor(R.color.gray));
                tv_amount_changing.setText(tv_sub_amount_0.getText().toString());
                amount=Integer.parseInt(splitSentence(tv_sub_amount_0.getText().toString()));
            }
        });

        tv_sub_amount_1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tv_sub_amount_1.setTextColor(getResources().getColor(R.color.wallet_color));
                tv_sub_amount_0.setTextColor(getResources().getColor(R.color.gray));
                tv_sub_amount_2.setTextColor(getResources().getColor(R.color.gray));
                tv_amount_changing.setText(tv_sub_amount_1.getText().toString());
                amount=Integer.parseInt(splitSentence(tv_sub_amount_1.getText().toString()));            }
        });

        tv_sub_amount_2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tv_sub_amount_2.setTextColor(getResources().getColor(R.color.wallet_color));
                tv_sub_amount_0.setTextColor(getResources().getColor(R.color.gray));
                tv_sub_amount_1.setTextColor(getResources().getColor(R.color.gray));
                tv_amount_changing.setText(tv_sub_amount_2.getText().toString());
                amount=Integer.parseInt(splitSentence(tv_sub_amount_2.getText().toString()));
            }
        });


    }

    @Override
    public Context getViewContext() {
        return this;
    }

    private String splitSentence(String sentence){
        String[] separated = sentence.split(" ");
       return  separated[0];
    }
}