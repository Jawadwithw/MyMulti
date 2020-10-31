package com.xbbxsnia.the2806.CapitanCar.DateInfoContract;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.FragmentManager;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.R;

import org.w3c.dom.Text;

import java.util.Objects;

public class DateInfoFragment extends BaseFragment implements BaseView{
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String date = "";
    private String time = "";
    private Button btn_date,btn_time;
    private TextView tv_date,tv_time;
    @Override
    public void setupViews() {
        initDateTimePicher();
        btn_date=(Button)rootView.findViewById(R.id.btn_date);
        btn_time=(Button)rootView.findViewById(R.id.btn_time);

        tv_date=(TextView)rootView.findViewById(R.id.tv_date);
        tv_time=(TextView)rootView.findViewById(R.id.tv_time);


        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(),"Datepickerdialog");
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(),"Timepickerdialog");

            }
        });
    }



    @Override
    public int getLayout() {
        return R.layout.layout_date_info;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        DateInfoFragment newFragment = new DateInfoFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        String date= tv_date.getText().toString();
        String time = tv_time.getText().toString();
        if (date.equals("") || time.equals("")){
            Toast.makeText(context, getResources().getString(R.string.fill_all_fileds), Toast.LENGTH_SHORT).show();
        return false;
        }else {
            UserSharedPrefManager prefManager=((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager();
            prefManager.saveUserData(OrderActivity.ORDER_DATE,date);
            prefManager.saveUserData(OrderActivity.ORDER_TIME,time);
        return true;
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    private void initDateTimePicher() {
        final PersianCalendar persianCalendar = new PersianCalendar();
        datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tv_date.setText(date);
            }
        }, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());


        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                tv_time.setText(time);
            }
        }, persianCalendar.get(PersianCalendar.HOUR_OF_DAY), persianCalendar.get(PersianCalendar.MINUTE), true);
    }
}
