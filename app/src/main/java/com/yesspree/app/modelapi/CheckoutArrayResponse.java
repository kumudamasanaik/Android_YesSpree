package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class CheckoutArrayResponse extends ResponseModel {

    @SerializedName("orders")
    private ArrayList<CheckoutResponse> orderList;

    public CheckoutArrayResponse() {
    }

    public ArrayList<CheckoutResponse> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<CheckoutResponse> orderList) {
        this.orderList = orderList;
    }
}