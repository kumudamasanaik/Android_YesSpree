package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class FetchCartSummaryResponse extends ResponseModel {

    @SerializedName(Constants.PAY_OPTIONS)
    ArrayList<PayOption> payOptionArrayList;
    @SerializedName(Constants.CART)
    ArrayList<ProductData> productDataArrayList;
    @SerializedName(Constants.SUMMARY)
    CartSummaryData cartSummaryData;
    @SerializedName(Constants.ORDERS)
    ArrayList<CheckoutOrderData> orderSummeryArrayList;
    @SerializedName(Constants.ADDRESS)
    AddressData address;


    public ArrayList<PayOption> getPayOptionArrayList() {
        return payOptionArrayList;
    }

    public void setPayOptionArrayList(ArrayList<PayOption> payOptionArrayList) {
        this.payOptionArrayList = payOptionArrayList;
    }

    public ArrayList<ProductData> getProductDataArrayList() {
        return productDataArrayList;
    }

    public void setProductDataArrayList(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList = productDataArrayList;
    }

    public CartSummaryData getCartSummaryData() {
        return cartSummaryData;
    }

    public void setCartSummaryData(CartSummaryData cartSummaryData) {
        this.cartSummaryData = cartSummaryData;
    }

    public ArrayList<CheckoutOrderData> getOrderSummeryArrayList() {
        return orderSummeryArrayList;
    }

    public void setOrderSummeryArrayList(ArrayList<CheckoutOrderData> orderSummeryArrayList) {
        this.orderSummeryArrayList = orderSummeryArrayList;
    }

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }
}
