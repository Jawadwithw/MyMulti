package com.xbbxsnia.the2806.ZipJet.MyOrdersContract;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdersListViewModel extends AndroidViewModel {
    private List<Order> doneOrders = new ArrayList<>();
    private List<Order> doingOrders = new ArrayList<>();

    public OrdersListViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Order> getDoneOrders() {
        for (int i = 0; i <= 10; i++){
            Order order = new Order();
            order.setImageUrl("https://picsum.photos/500/500?random=" + i);
            order.setState("done");
            List<String> services = new ArrayList<>();
            services.add("اتو");
            services.add("لکه بری");
            services.add("نرم کننده");
            services.add("اتو");
            order.setServices(services);
            order.setPrice(6000);
            order.setTitle("پیراهن سفید");
            doneOrders.add(order);
        }
        return doneOrders;
    }

    public List<Order> getDoingOrders() {
        for (int i = 0; i<= 10; i++){
            Order order = new Order();
            order.setImageUrl("https://picsum.photos/500/500?random=" + i);
            order.setState("sent");
            List<String> services = new ArrayList<>();
            services.add("اتو");
            services.add("لکه بری");
            services.add("نرم کننده");
            services.add("اتو");
            order.setServices(services);
            order.setPrice(10000);
            order.setTitle("پیراهن سفید");
            order.setDeliverAvatar("https://picsum.photos/300/300?random=" + i);
            order.setPlateNumber("11 222 ب 11");
            order.setDeliveryType(getRandom(new String[]{"ordinary", "express"}));
            order.setDeliveryPrice(7000);
            order.setDeliverName("داوود افتخاری");
            order.setDeliverPhoneNumber("+989304154108");
            doingOrders.add(order);
        }
        return doingOrders;
    }

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
