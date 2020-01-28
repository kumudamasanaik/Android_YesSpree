package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSubcrInput {


    @Expose
    @SerializedName("pay_type")
    private String pay_type;
    @Expose
    @SerializedName("is_doorbellring")
    private String is_doorbellring;
    @Expose
    @SerializedName("is_alternate")
    private String is_alternate;
    @Expose
    @SerializedName("start_date")
    private String start_date;
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("id_sku")
    private String id_sku;
    @Expose
    @SerializedName("id_product")
    private String id_product;
    @Expose
    @SerializedName("subscription_type")
    private String subscription_type;
    @Expose
    @SerializedName("day")
    private String day;


    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getIs_doorbellring() {
        return is_doorbellring;
    }

    public void setIs_doorbellring(String is_doorbellring) {
        this.is_doorbellring = is_doorbellring;
    }

    public String getIs_alternate() {
        return is_alternate;
    }

    public void setIs_alternate(String is_alternate) {
        this.is_alternate = is_alternate;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId_sku() {
        return id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
