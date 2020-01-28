package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class CheckoutResponse extends ResponseModel {

    @SerializedName(Constants.TITLE)
    private String title;
    @SerializedName(Constants.CART)
    private ArrayList<OrderedProduct> skuList;
    @SerializedName(Constants.ORDER)
    private OrderData orderData;
    @SerializedName(Constants.ADDRESS)
    private AddressData addressData;

    public CheckoutResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<OrderedProduct> getSkuList() {
        return skuList;
    }

    public void setSkuList(ArrayList<OrderedProduct> skuList) {
        this.skuList = skuList;
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

}