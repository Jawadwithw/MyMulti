package com.xbbxsnia.the2806.ZipJet.OrderContract;

import com.xbbxsnia.the2806.ZipJet.models.Order;

public interface OnNextOrBackClickedCallBack {
    void onNextClick(int position, Order order);
    void onBackClicked();
}
