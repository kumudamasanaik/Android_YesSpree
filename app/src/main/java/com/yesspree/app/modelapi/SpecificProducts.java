package com.yesspree.app.modelapi;

import com.google.gson.annotations.SerializedName;
import com.yesspree.app.constatnts.Constants;

import java.util.ArrayList;

public class SpecificProducts {
    @SerializedName(Constants.TITLE)
    String title;
    @SerializedName(Constants.PRODUCTS)
    ArrayList<ProductData> productDataArrayList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ProductData> getProductDataArrayList() {
        return productDataArrayList;
    }

    public void setProductDataArrayList(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList = productDataArrayList;
    }
}
