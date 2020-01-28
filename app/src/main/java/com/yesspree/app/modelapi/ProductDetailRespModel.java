package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 28-05-2018.
 */

public class ProductDetailRespModel extends ResponseModel {


    @SerializedName(Constants.SIMILER_DATA)
    SpecificProducts similerData;



    @SerializedName(Constants.SPECIFIC_PRODUCT)
    ArrayList<SpecificProducts> footerData;


    @SerializedName(Constants.SUMMARY)
    CartSummaryData cartSummaryData;


    @SerializedName(Constants.PRODUCTS)
    ArrayList<ProductData> productData;


    public SpecificProducts getSimilerData() {
        return similerData;
    }

    public void setSimilerData(SpecificProducts similerData) {
        this.similerData = similerData;
    }

    public   ArrayList<SpecificProducts> getFooterData() {
        return footerData;
    }

    public void setFooterData( ArrayList<SpecificProducts> footerData) {
        this.footerData = footerData;
    }

    public CartSummaryData getCartSummaryData() {
        return cartSummaryData;
    }

    public void setCartSummaryData(CartSummaryData cartSummaryData) {
        this.cartSummaryData = cartSummaryData;
    }

    public ArrayList<ProductData> getProductData() {
        return productData;
    }

    public void setProductData(ArrayList<ProductData> productData) {
        this.productData = productData;
    }

}
