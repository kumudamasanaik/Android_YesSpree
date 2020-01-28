package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

public class CartSummaryData {

    @Expose
    @SerializedName(Constants.GRAND_TOTALL)
    private String grand_total;
    @Expose
    @SerializedName(Constants.DELIVERY_CHARGE)
    private String delivery_charge;
    @Expose
    @SerializedName(Constants.SELLING_PRICE)
    private String selling_price;
    @Expose
    @SerializedName(Constants.MRP)
    private String mrp;
    @Expose
    @SerializedName(Constants.REALIZATION)
    private String realization;
    @Expose
    @SerializedName(Constants.CART_COUNT)
    private String cart_count;

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getRealization() {
        return realization;
    }

    public void setRealization(String realization) {
        this.realization = realization;
    }

    public String getCart_count() {
        return cart_count;
    }

    public void setCart_count(String cart_count) {
        this.cart_count = cart_count;
    }
}
