package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 08-06-2018.
 */

public class MyOrdersRespModel extends ResponseModel {
    @SerializedName(Constants.ORDERS)
    ArrayList<MyOrders> orderDataArrayList;

    public ArrayList<MyOrders> getOrderDataArrayList() {
        return orderDataArrayList;
    }

    public void setOrderDataArrayList(ArrayList<MyOrders> orderDataArrayList) {
        this.orderDataArrayList = orderDataArrayList;
    }

    public class MyOrders {
        @SerializedName(Constants.ORDER)
        OrderData orderData;
        @SerializedName(Constants.CART)
        private ArrayList<OrderedProduct> productList;
        @SerializedName(Constants.ADDRESS)
        AddressData addressData;

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
    }
}
