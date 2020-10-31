package com.xbbxsnia.the2806.ZipJet.OrderContract.Invoice;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.xbbxsnia.the2806.CapitanCar.Data.CreateOrderNotification;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.Main2806.Main2806.ColleagueContract.ColleagueActivity;
import com.xbbxsnia.the2806.Main2806.Main2806.Data.DialogGenerator;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnOrderConfirmed;
import com.xbbxsnia.the2806.ZipJet.models.ClothNumberModel;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.IS_MY_ORDER;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.LOCATION_GEO;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_FOR;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_ID;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.ORDER_STATUS;
import static com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity.PENDING;

public class InvoiceViewModel extends AndroidViewModel {
    private List<ClothNumberModel> clothNumberModels = new ArrayList<>();
    private DialogGenerator dialogGenerator;
    private Dialog progressDialog;
    private String order_id;

    public InvoiceViewModel(@NonNull Application application) {
        super(application);
        dialogGenerator = new DialogGenerator();
    }

    public void postOrder(Context context, Order order, OnOrderConfirmed onOrderConfirmed) {
        saveObject(context, order, onOrderConfirmed);
    }


    private void saveObject(Context context, Order order, OnOrderConfirmed onOrderConfirmed) {
        ParseObject parseObject = OrderDetails("tbl_" + G.currentUser.getUsername(), order, true);
        progressDialog = dialogGenerator.progressDialog(context);
        progressDialog.show();
        parseObject.saveInBackground(e -> {
            if (e == null) {

                order_id = parseObject.getObjectId();
                OrderDetails("order_" + ColleagueActivity.ZIP_JET, order, false).saveInBackground(e1 -> {
                    progressDialog.dismiss();
                    if (e1 == null) {
                        CreateOrderNotification orderNotification = new CreateOrderNotification();
                        //send notific to admin head captain
                        orderNotification.createOrderNotification("09120396076", context.getResources().getString(R.string.order_notific_message));
                        onOrderConfirmed.orderConfirmed(true);
                    }
                });

            } else {
                Toast.makeText(context, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private ParseObject OrderDetails(String tbl_name, Order order, boolean isMine) {
        ParseObject parseObject = new ParseObject(tbl_name);

        //get user's location for parse
        ParseGeoPoint userGeoPoint = new ParseGeoPoint(order.getUserLat(),
                order.getUserLng());
        G.currentUser.put(LOCATION_GEO, userGeoPoint);
        G.currentUser.saveInBackground();

        parseObject.put((OrderActivity.NAME), order.getFull_name());

        parseObject.put((OrderActivity.PROBLEM_DESC), order.getDescription());
        parseObject.put((OrderActivity.ORDER_TIME), order.getTime());
        parseObject.put((OrderActivity.ORDER_DATE), order.getDate());
        parseObject.put(LOCATION_GEO, userGeoPoint);
        parseObject.put((OrderActivity.TOTAL_ADDRESS), order.getUserAddress());
        parseObject.put(OrderActivity.ORDER_FROM, G.currentUser.getUsername());
        parseObject.put(OrderActivity.ORDER_CLOTH_TYPE, order.getTitle());
        parseObject.put(OrderActivity.ORDER_COLOR, order.getColor());
        parseObject.put(ORDER_FOR, ColleagueActivity.ZIP_JET);
        if (isMine) {
            parseObject.put(IS_MY_ORDER, true);
            parseObject.put(ORDER_STATUS, PENDING);
        } else {
            parseObject.put(ORDER_ID, order_id);

        }

        return parseObject;

    }


}
