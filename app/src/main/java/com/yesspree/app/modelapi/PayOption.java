package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayOption {


    @Expose
    @SerializedName("timestamp")
    private String timestamp;
    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("sort_order")
    private String sort_order;
    @Expose
    @SerializedName("pay_url")
    private String pay_url;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("logo")
    private String logo;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("pay_option")
    private String pay_option;
    @Expose
    @SerializedName("id")
    private String id;


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPay_option() {
        return pay_option;
    }

    public void setPay_option(String pay_option) {
        this.pay_option = pay_option;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
