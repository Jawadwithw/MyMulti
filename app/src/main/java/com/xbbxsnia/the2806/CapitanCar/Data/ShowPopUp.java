package com.xbbxsnia.the2806.CapitanCar.Data;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xbbxsnia.the2806.R;

public class ShowPopUp extends Activity implements View.OnClickListener {

    Button ok;
    Button cancel;

    boolean click = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("customdata"));
        setContentView(R.layout.popupdialog);
        ok = (Button)findViewById(R.id.popOkB);
        ok.setOnClickListener(this);
        cancel = (Button)findViewById(R.id.popCancelB);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        finish();
    }
}