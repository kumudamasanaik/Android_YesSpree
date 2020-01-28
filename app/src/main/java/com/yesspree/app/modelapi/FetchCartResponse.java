package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;


public class FetchCartResponse extends ResponseModel {
    @SerializedName(Constants.CART)
    private ArrayList<ProductData> productList;
    @SerializedName(Constants.SUMMARY)
    private CartSummaryData cartCartSummaryDataData;

    @Expose
    @SerializedName(Constants.ADVERTISEMENT)
    ArrayList<Advertisement> advertisement;


    public ArrayList<ProductData> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductData> productList) {
        this.productList = productList;
    }

    public ArrayList<Advertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(ArrayList<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }

    public CartSummaryData getCartCartSummaryDataData() {
        return cartCartSummaryDataData;
    }

    public void setCartCartSummaryDataData(CartSummaryData cartCartSummaryDataData) {
        this.cartCartSummaryDataData = cartCartSummaryDataData;
    }
}