package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Parveen Dala on 23 May, 2017
 * Fugenx Technologies, Bengaluru
 * NamDhari
 */

public class OrderData implements Parcelable {

    public static final Creator<OrderData> CREATOR = new Creator<OrderData>() {
        @Override
        public OrderData createFromParcel(Parcel source) {
            return new OrderData(source);
        }

        @Override
        public OrderData[] newArray(int size) {
            return new OrderData[size];
        }
    };
    @SerializedName("_id")
    private String id;
    @SerializedName("id_customer")
    private String customerId;
    @SerializedName("id_warehouse")
    private String warehouseId;
    @SerializedName("order_no")
    private String orderNumber;
    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("total_amount")
    private String totalAmount;
    private String coupon;
    @SerializedName("total_mrp")
    private String totalMrp;
    @SerializedName("total_items")
    private String totalItems;
    @SerializedName("total_promo_code_discount")
    private String totalPromoCodeDiscount;
    @SerializedName("id_coupon")
    private String couponId;
    @SerializedName("total_selling_discount")
    private String totalSellingDiscount;
    @SerializedName("delivery_charge")
    private String deliveryCharge;
    @SerializedName("total_selling_price")
    private String totalSellingPrice;
    @SerializedName("total_discount")
    private String totalDiscount;
    @SerializedName("pay_type")
    private String payType;
    @SerializedName("total_paid")
    private String totalPaid;
    @SerializedName("delivery_type")
    private String deliveryType;
    @SerializedName("assign_delivery")
    private String assignDelivery;
    @SerializedName("delivered_on")
    private String deliveredOn;
    @SerializedName("cancelled_on")
    private String cancelledOn;
    private String added;
    private String updated;
    @SerializedName("pay_response")
    private String payResponse;
    @SerializedName("pay_status")
    private String payStatus;
    @SerializedName("invoice_no")
    private String invoiceNumber;
    @SerializedName("delivery_no")
    private String deliveryNumber;
    @SerializedName("invoice_date")
    private String invoiceDate;
    @SerializedName("delivery_slot")
    private String deliverySlot;
    private String tempOrderStatus;
    private AddressData addressData;
    @SerializedName("delivery_slot_time")
    private String delivery_slot_time;
    @SerializedName("grand_total")
    private String grand_total;

    @SerializedName("product_name")
    private String product_name;
    @SerializedName("product_pic")
    private String product_pic;


    public OrderData() {
    }

    protected OrderData(Parcel in) {
        this.id = in.readString();
        this.customerId = in.readString();
        this.warehouseId = in.readString();
        this.orderNumber = in.readString();
        this.orderStatus = in.readString();
        this.totalAmount = in.readString();
        this.coupon = in.readString();
        this.totalMrp = in.readString();
        this.totalItems = in.readString();
        this.totalPromoCodeDiscount = in.readString();
        this.couponId = in.readString();
        this.totalSellingDiscount = in.readString();
        this.deliveryCharge = in.readString();
        this.totalSellingPrice = in.readString();
        this.totalDiscount = in.readString();
        this.payType = in.readString();
        this.totalPaid = in.readString();
        this.deliveryType = in.readString();
        this.assignDelivery = in.readString();
        this.deliveredOn = in.readString();
        this.cancelledOn = in.readString();
        this.added = in.readString();
        this.updated = in.readString();
        this.payResponse = in.readString();
        this.payStatus = in.readString();
        this.invoiceNumber = in.readString();
        this.deliveryNumber = in.readString();
        this.invoiceDate = in.readString();
        this.deliverySlot = in.readString();
        this.tempOrderStatus = in.readString();
        this.grand_total = in.readString();
        this.product_pic = in.readString();
        this.product_name = in.readString();
        this.addressData = in.readParcelable(AddressData.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getTotalMrp() {
        return totalMrp;
    }

    public void setTotalMrp(String totalMrp) {
        this.totalMrp = totalMrp;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalPromoCodeDiscount() {
        return totalPromoCodeDiscount;
    }

    public void setTotalPromoCodeDiscount(String totalPromoCodeDiscount) {
        this.totalPromoCodeDiscount = totalPromoCodeDiscount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getTotalSellingDiscount() {
        return totalSellingDiscount;
    }

    public void setTotalSellingDiscount(String totalSellingDiscount) {
        this.totalSellingDiscount = totalSellingDiscount;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getAssignDelivery() {
        return assignDelivery;
    }

    public void setAssignDelivery(String assignDelivery) {
        this.assignDelivery = assignDelivery;
    }

    public String getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public String getCancelledOn() {
        return cancelledOn;
    }

    public void setCancelledOn(String cancelledOn) {
        this.cancelledOn = cancelledOn;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getPayResponse() {
        return payResponse;
    }

    public void setPayResponse(String payResponse) {
        this.payResponse = payResponse;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDeliverySlot() {
        return deliverySlot;
    }

    public void setDeliverySlot(String deliverySlot) {
        this.deliverySlot = deliverySlot;
    }

    public String getTempOrderStatus() {
        return tempOrderStatus;
    }

    public void setTempOrderStatus(String tempOrderStatus) {
        this.tempOrderStatus = tempOrderStatus;
    }

    public AddressData getAddressData() {
        return addressData;
    }


    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    public String getDelivery_slot_time() {
        return delivery_slot_time;
    }

    public void setDelivery_slot_time(String delivery_slot_time) {
        this.delivery_slot_time = delivery_slot_time;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.customerId);
        dest.writeString(this.warehouseId);
        dest.writeString(this.orderNumber);
        dest.writeString(this.orderStatus);
        dest.writeString(this.totalAmount);
        dest.writeString(this.coupon);
        dest.writeString(this.totalMrp);
        dest.writeString(this.totalItems);
        dest.writeString(this.totalPromoCodeDiscount);
        dest.writeString(this.couponId);
        dest.writeString(this.totalSellingDiscount);
        dest.writeString(this.deliveryCharge);
        dest.writeString(this.totalSellingPrice);
        dest.writeString(this.totalDiscount);
        dest.writeString(this.payType);
        dest.writeString(this.totalPaid);
        dest.writeString(this.deliveryType);
        dest.writeString(this.assignDelivery);
        dest.writeString(this.deliveredOn);
        dest.writeString(this.cancelledOn);
        dest.writeString(this.added);
        dest.writeString(this.updated);
        dest.writeString(this.payResponse);
        dest.writeString(this.payStatus);
        dest.writeString(this.invoiceNumber);
        dest.writeString(this.deliveryNumber);
        dest.writeString(this.invoiceDate);
        dest.writeString(this.deliverySlot);
        dest.writeString(this.tempOrderStatus);
        dest.writeString(this.grand_total);
        dest.writeString(this.product_pic);
        dest.writeString(this.product_name);
        dest.writeParcelable(this.addressData, flags);
    }
}
