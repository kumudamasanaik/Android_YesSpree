package com.yesspree.app.modelapi;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class MyOrderStatusRes extends ResponseModel {
    @SerializedName(Constants.ORDER)
    OrderData orderData;
    @SerializedName(Constants.CART)
    private ArrayList<OrderedProduct> productList;
    @SerializedName(Constants.ADDRESS)
    AddressData addressData;
    @SerializedName(Constants.TRACK)
    ArrayList<OrderTrackData> orderTrackDataArrayList;

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }

    public ArrayList<OrderedProduct> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<OrderedProduct> productList) {
        this.productList = productList;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    public ArrayList<OrderTrackData> getOrderTrackDataArrayList() {
        return orderTrackDataArrayList;
    }

    public void setOrderTrackDataArrayList(ArrayList<OrderTrackData> orderTrackDataArrayList) {
        this.orderTrackDataArrayList = orderTrackDataArrayList;
    }
}
