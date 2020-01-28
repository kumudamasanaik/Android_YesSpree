package com.yesspree.app.modelapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

/**
 * Created by FuGenX-14 on 24-05-2018.
 */

public class ProductMainListingRespModel extends ResponseModel {

    @Expose
    @SerializedName(Constants.TOTAL_SKU)
    private String total_sku;


    @SerializedName(Constants.CAT_DISCOUNT)
    ArrayList<CatDiscount> catDiscountArrayList;

    @SerializedName(Constants.SUB_CATEGORY)
    ArrayList<Category> categoryArrayList;

    @SerializedName(Constants.SPECIFIC_PRODUCT)
    ArrayList<SpecificProducts> specificProducts;

    @SerializedName(Constants.SUMMARY)
    CartSummaryData cartSummaryData;

    @SerializedName(Constants.PRODUCTS)
    ArrayList<ProductData> productDataArrayList;
    @Expose
    @SerializedName(Constants.ADVERTISEMENT)
    ArrayList<Advertisement> advertisement;

    public ArrayList<Advertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(ArrayList<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }

    public ArrayList<CatDiscount> getCatDiscountArrayList() {
        return catDiscountArrayList;
    }

    public void setCatDiscountArrayList(ArrayList<CatDiscount> catDiscountArrayList) {
        this.catDiscountArrayList = catDiscountArrayList;
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }

    public ArrayList<SpecificProducts> getSpecificProducts() {
        return specificProducts;
    }

    public void setSpecificProducts(ArrayList<SpecificProducts> specificProducts) {
        this.specificProducts = specificProducts;
    }

    public CartSummaryData getCartSummaryData() {
        return cartSummaryData;
    }

    public void setCartSummaryData(CartSummaryData cartSummaryData) {
        this.cartSummaryData = cartSummaryData;
    }

    public ArrayList<ProductData> getProductDataArrayList() {
        return productDataArrayList;
    }

    public void setProductDataArrayList(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList = productDataArrayList;
    }

    public String getTotal_sku() {
        return total_sku;
    }

    public void setTotal_sku(String total_sku) {
        this.total_sku = total_sku;
    }

}
