package com.xbbxsnia.the2806.ZipJet.OrderContract.ChooseOrderTimeContract;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.xbbxsnia.the2806.ZipJet.models.Order;
import com.xbbxsnia.the2806.ZipJet.models.OrderTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChooseOrderTimeViewModel extends AndroidViewModel {
    private List<OrderTime> days = new ArrayList<>();
    private List<OrderTime> hours = new ArrayList<>();

    public ChooseOrderTimeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<OrderTime> getDays(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dayAfterTomorrow = calendar.getTime();

        PersianCalendar todayTime = new PersianCalendar();
        todayTime.setTime(today);

        PersianCalendar tomorrowTime = new PersianCalendar();
        tomorrowTime.setTime(tomorrow);

        PersianCalendar dayAfterTomorrowTime = new PersianCalendar();
        dayAfterTomorrowTime.setTime(dayAfterTomorrow);

        OrderTime todayObject = new OrderTime();
        todayObject.setTime(todayTime.getPersianWeekDayName());
        todayObject.setCost(todayTime.getPersianLongDate());

        OrderTime tomorrowObject = new OrderTime();
        tomorrowObject.setTime(tomorrowTime.getPersianWeekDayName());
        tomorrowObject.setCost(tomorrowTime.getPersianLongDate());

        OrderTime dayAfterTomorrowObject = new OrderTime();
        dayAfterTomorrowObject.setTime(dayAfterTomorrowTime.getPersianWeekDayName());
        dayAfterTomorrowObject.setCost(dayAfterTomorrowTime.getPersianLongDate());

        days.add(todayObject);
        days.add(tomorrowObject);
        days.add(dayAfterTomorrowObject);

        return days;
    }

    public List<OrderTime> getHours() {
        OrderTime time1 = new OrderTime();
        time1.setTime("10:30 قبل از ظهر");
        hours.add(time1);

        OrderTime time2 = new OrderTime();
        time2.setTime("11:30 قبل از ظهر");
        hours.add(time2);

        OrderTime time3 = new OrderTime();
        time3.setTime("02:00 بعد از ظهر");
        hours.add(time3);

        return hours;
    }
}
