package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class OrderSummery {


    @Expose
    @SerializedName("delivery_message")
    private String delivery_message;
    @Expose
    @SerializedName("show_slot")
    private String show_slot;
    @Expose
    @SerializedName("order_id")
    private int order_id;
    @Expose
    @SerializedName("total_promo_code_discount")
    private int total_promo_code_discount;
    @Expose
    @SerializedName("grand_total")
    private int grand_total;
    @Expose
    @SerializedName("total_discount")
    private int total_discount;
    @Expose
    @SerializedName("delivery_charge")
    private String delivery_charge;
    @Expose
    @SerializedName("total_selling_discount")
    private int total_selling_discount;
    @Expose
    @SerializedName("total_selling_price")
    private int total_selling_price;
    @Expose
    @SerializedName("total_mrp")
    private int total_mrp;
    @Expose
    @SerializedName("delivery_type")
    private String delivery_type;
    @Expose
    @SerializedName("added")
    private String added;
    @Expose
    @SerializedName("id_warehouse")
    private String id_warehouse;
    @Expose
    @SerializedName("order_status")
    private String order_status;
    @Expose
    @SerializedName("order_no")
    private String order_no;
    @Expose
    @SerializedName("id_customer")
    private int id_customer;
    @Expose
    @SerializedName("total_items")
    private int total_items;

    @SerializedName(Constants.DELIVERYSLOT)
    ArrayList<DeliverySlotData> deliverySlotDataArrayList;

    public String getDelivery_message() {
        return delivery_message;
    }

    public void setDelivery_message(String delivery_message) {
        this.delivery_message = delivery_message;
    }

    public String getShow_slot() {
        return show_slot;
    }

    public void setShow_slot(String show_slot) {
        this.show_slot = show_slot;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotal_promo_code_discount() {
        return total_promo_code_discount;
    }

    public void setTotal_promo_code_discount(int total_promo_code_discount) {
        this.total_promo_code_discount = total_promo_code_discount;
    }

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public int getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(int total_discount) {
        this.total_discount = total_discount;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public int getTotal_selling_discount() {
        return total_selling_discount;
    }

    public void setTotal_selling_discount(int total_selling_discount) {
        this.total_selling_discount = total_selling_discount;
    }

    public int getTotal_selling_price() {
        return total_selling_price;
    }

    public void setTotal_selling_price(int total_selling_price) {
        this.total_selling_price = total_selling_price;
    }

    public int getTotal_mrp() {
        return total_mrp;
    }

    public void setTotal_mrp(int total_mrp) {
        this.total_mrp = total_mrp;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getId_warehouse() {
        return id_warehouse;
    }

    public void setId_warehouse(String id_warehouse) {
        this.id_warehouse = id_warehouse;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public ArrayList<DeliverySlotData> getDeliverySlotDataArrayList() {
        return deliverySlotDataArrayList;
    }

    public void setDeliverySlotDataArrayList(ArrayList<DeliverySlotData> deliverySlotDataArrayList) {
        this.deliverySlotDataArrayList = deliverySlotDataArrayList;
    }
}
