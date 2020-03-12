package com.example.foodliveryapp.data.order;

import android.content.Context;
import android.widget.TextView;

public class DetailedOrder {

    Context ctx;
    private TextView orderStatus;
    private TextView orderCode;
    private TextView customerAddress;
    private TextView customerName;
    private TextView customerPhone;
    private TextView customerNote;
    private TextView orderItems;
    private TextView priceValue;
    private TextView priceType;
    private TextView restaurantAddress;
    private TextView restaurantPhone;
    private TextView officePhone;

    public DetailedOrder(Context ctx,TextView orderStatus, TextView orderCode, TextView customerAddress, TextView customerName, TextView customerPhone, TextView customerNote, TextView orderItems, TextView priceValue, TextView priceType, TextView restaurantAddress, TextView restaurantPhone, TextView officePhone) {
        this.ctx = ctx;
        this.orderStatus = orderStatus;
        this.orderCode = orderCode;
        this.customerAddress = customerAddress;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerNote = customerNote;
        this.orderItems = orderItems;
        this.priceValue = priceValue;
        this.priceType = priceType;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPhone = restaurantPhone;
        this.officePhone = officePhone;
    }

    public void setOrder(String orderStatus, String orderCode, String customerAddress, String customerName, String customerPhone, String customerNote, String orderItems, String priceValue, String priceType, String  restaurantAddress, String restaurantPhone, String officePhone){

        this.orderStatus.setText(orderStatus);
        this.orderCode.setText(orderCode);
        this.customerAddress.setText(customerAddress);
        this.customerName.setText(customerName);
        this.customerPhone.setText(customerPhone);
        this.customerNote.setText(customerNote);
        this.orderItems.setText(orderItems);
        this.priceValue.setText(priceValue);
        this.priceType.setText(priceType);
        this.restaurantAddress.setText(restaurantAddress);
        this.restaurantPhone.setText(restaurantPhone);
        this.officePhone.setText(officePhone);
    }
}
