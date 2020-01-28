package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;


public class CheckoutOrderData {


    @SerializedName("order_id")
    private String orderId;
    @SerializedName("id_customer")
    private String customerId;
    @SerializedName("id_warehouse")
    private String warehouseId;
    @SerializedName("order_no")
    private String orderNumber;
    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("delivery_type")
    private String deliveryType;
    @SerializedName("total_items")
    private String totalItems;
    @SerializedName("total_mrp")
    private String totalMrp;
    @SerializedName("delivery_charge")
    private String deliveryCharge;
    @SerializedName("total_selling_price")
    private String totalSellingPrice;
    @SerializedName("total_discount")
    private String totalDiscount;
    @SerializedName("total_selling_discount")
    private String totalSellingDiscount;
    @SerializedName("grand_total")
    private String grandTotal;
    @SerializedName("show_slot")
    private int showSlot;
    @SerializedName("delivery_message")
    private String deliveryMessage;
    private String added;
    @SerializedName("deliveryslot")
    private ArrayList<DeliverySlotData> deliverySlotList;


    @SerializedName(Constants.DELIVERY_SLOT)
    String delivery_slot;


    public CheckoutOrderData() {
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalMrp() {
        return totalMrp;
    }

    public void setTotalMrp(String totalMrp) {
        this.totalMrp = totalMrp;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(String totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getTotalSellingDiscount() {
        return totalSellingDiscount;
    }

    public void setTotalSellingDiscount(String totalSellingDiscount) {
        this.totalSellingDiscount = totalSellingDiscount;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getShowSlot() {
        return showSlot;
    }

    public void setShowSlot(int showSlot) {
        this.showSlot = showSlot;
    }

    public String getDeliveryMessage() {
        return deliveryMessage;
    }

    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public ArrayList<DeliverySlotData> getDeliverySlotList() {
        return deliverySlotList;
    }

    public void setDeliverySlotList(ArrayList<DeliverySlotData> deliverySlotList) {
        this.deliverySlotList = deliverySlotList;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }
}
